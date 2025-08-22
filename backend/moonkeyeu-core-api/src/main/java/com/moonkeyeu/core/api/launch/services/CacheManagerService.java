package com.moonkeyeu.core.api.launch.services;

import org.springframework.cache.Cache;
import java.util.Optional;

public interface CacheManagerService {

    void evictAll(String cacheName);
    void evictById(String cacheName, String cacheKey);
    <T> Optional<T> getByCacheNameAndKey(String cacheName, String cacheKey, Class<T> type);
    Cache getCacheByName(String cacheName);
}
