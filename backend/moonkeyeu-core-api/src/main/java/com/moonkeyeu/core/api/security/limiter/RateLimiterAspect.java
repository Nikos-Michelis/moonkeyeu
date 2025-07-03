package com.moonkeyeu.core.api.security.limiter;

import com.moonkeyeu.core.api.security.util.ClientInfoExtractor;
import com.moonkeyeu.core.api.settings.exceptions.RateLimitException;
import io.github.bucket4j.Bucket;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Map;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class RateLimiterAspect {

    private final BucketManager bucketManager;
    private final HttpServletRequest request;
    private final ClientInfoExtractor clientInfoExtractor;


    @Before("@annotation(com.moonkeyeu.core.api.security.limiter.RateLimited))")
    public void rateLimiter(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        RateLimited rateLimiter = method.getAnnotation(RateLimited.class);
        if (rateLimiter == null) {
            return;
        }
        Bucket bucket = bucketManager.resolveBucket(getKey(method), rateLimiter.requests(), rateLimiter.durationSeconds());
        if (!bucket.tryConsume(1)) {
            throw new RateLimitException("Too many requests. Please try again later.");
        }
    }

    /**
     * Constructs a unique key by combining the client IP address and the method name.
     * This key is used to identify the token bucket for rate limiting.
     *
     * @param method the method being invoked
     * @return a unique key string combining client IP and method name
     */
    private String getKey(Method method) {
        String ipAddress = getClientRemoteAddress();
        return ipAddress + ":" + method.getName();
    }

    private String getClientRemoteAddress() {
        Map<String, String> clientInfo = clientInfoExtractor.getClientInfo(request);
        String ipAddress = clientInfo.get("remote_address");
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            throw new IllegalArgumentException("Unable to retrieve client IP address.");
        }
        return ipAddress;
    }
}
