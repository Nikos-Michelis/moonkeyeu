package com.moonkeyeu.core.api.security.limiter;

import io.github.bucket4j.Bucket;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
public class BucketManager {
    @Value("${application.limiter.threshold}")
    private long THRESHOLD;
    private final ConcurrentHashMap<String, Bucket> tokenBucket = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Long> lastAccessTime = new ConcurrentHashMap<>();

    /**
     * Resolves a token bucket for the given key.
     * <p>
     * If a bucket for the key already exists, it returns the existing bucket.
     * Otherwise, it creates a new bucket with the specified request limit and refill duration.
     * The bucket is used for rate limiting by controlling the number of tokens available
     * per time window.
     * </p>
     *
     * @param key             the identifier for the token bucket (e.g., user ID, API key)
     * @param requests        the maximum number of tokens allowed in the bucket (capacity)
     * @param durationSeconds the duration in seconds over which the bucket refills the tokens
     * @return the resolved or newly created {@link Bucket} for rate limiting
     */
    public Bucket resolveBucket(String key, int requests, int durationSeconds) {
        log.debug("Resolving bucket for key: {}", key);
        lastAccessTime.put(key, System.currentTimeMillis());
        return tokenBucket.computeIfAbsent(key, k -> createBucket(requests, durationSeconds));
    }

    /**
     *
     * The bucket uses a greedy refill strategy, which ensures that tokens
     * are refilled at a consistent rate based on the given duration and capacity.
     * Example: 100 tokens per 60 seconds → 1 token every 600 milliseconds (i.e., 0.6 seconds).
     * ms/token = (time (seconds) / requests) x 1000
     * @param requests        The maximum number of requests allowed within the specified time window.
     * @param durationSeconds The duration (in seconds) of the time window for the bucket's capacity.
     * @return A {@link Bucket} instance configured with the specified rate limit.
     * </pre>
     */
    private Bucket createBucket(int requests, int durationSeconds) {
        log.info("Creating new bucket with limit {} requests per {} seconds", requests, durationSeconds);
        return Bucket.builder()
                .addLimit(limit -> limit.capacity(requests)
                        .refillGreedy(requests, Duration.ofSeconds(durationSeconds)))
                .build();
    }

    @Scheduled(fixedRateString  = "${application.limiter.scheduled-time}")
    public void cleanupStaleBuckets() {
        long now = System.currentTimeMillis();

       // log.debug("Number of entries in lastAccessTime: {}", lastAccessTime.size());
        Iterator<Map.Entry<String, Long>> iterator = lastAccessTime.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Long> entry = iterator.next();
            String key = entry.getKey();
            long lastAccess = entry.getValue();

           // log.debug("Processing key: {}, Last Access: {}, Time Diff: {}", key, lastAccess, now - lastAccess);

            if (now - lastAccess > THRESHOLD) {
                log.info("Cleaning up stale bucket for key: {}", key);
                tokenBucket.remove(key);
                iterator.remove();
            }
        }

        log.debug("Completed cleanup of stale buckets at {}", now);
    }
}