package com.moonkeyeu.core.api.security.services.jwt;

import com.moonkeyeu.core.api.settings.exceptions.InvalidHeadersException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

@Service
@Slf4j
public class JwtRequestExtractor {

    public Optional<String> extractToken(HttpServletRequest request) {
        try {
            return Optional.ofNullable(extractTokenBearer(request));
        } catch (InvalidHeadersException e){
            return Optional.ofNullable(extractRefreshTokenCookie(request));
        }
    }

    public String extractTokenBearer(HttpServletRequest request) throws InvalidHeadersException {
        String authHeader = request.getHeader("Authorization");
       // log.debug("Authorization header: {}", authHeader);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new InvalidHeadersException("Missing or invalid Authorization header.");
        }
        return authHeader.substring(7);
    }
    public String extractRefreshTokenCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            return Arrays.stream(cookies)
                    .filter(cookie -> "refresh_token".equals(cookie.getName()))
                    .map(Cookie::getValue)
                    .findFirst()
                    .orElse(null);
        }
        return null;
    }
}
