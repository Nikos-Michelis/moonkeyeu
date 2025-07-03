package com.moonkeyeu.core.api.configuration.caching;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.setCaffeine(Caffeine.newBuilder()
                .expireAfterWrite(1, TimeUnit.HOURS)
                .maximumSize(5000));
        cacheManager.registerCustomCache("filters-cache",
                Caffeine.newBuilder()
                        .maximumSize(3000)
                        .build()
        );
        cacheManager.setCacheNames(List.of(
                "filters-cache",
                "launch-cache",
                "astronaut-cache",
                "launcher-cache",
                "spacecraft-cache",
                "program-cache",
                "agencies-cache",
                "pad-cache",
                "rocket-cache",
                "rocket-images-cache",
                "agencies-images-cache",
                "spacecraft-images-cache",
                "pads-images-cache",
                "locations-images-cache",
                "astronauts-images-cache",
                "launchers-images-cache",
                "mission-patches-images-cache",
                "programs-images-cache"
        ));
        return cacheManager;
    }
}
