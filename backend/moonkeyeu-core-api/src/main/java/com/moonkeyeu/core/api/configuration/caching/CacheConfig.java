package com.moonkeyeu.core.api.configuration.caching;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.moonkeyeu.core.api.configuration.utils.CacheNames;
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
        cacheManager.registerCustomCache(
                CacheNames.NASA_APOD_CACHE,
                Caffeine.newBuilder()
                        .maximumSize(3000)
                        .build()
        );
        cacheManager.registerCustomCache(
                CacheNames.FILTERS_CACHE,
                Caffeine.newBuilder()
                        .maximumSize(10)
                        .build()
        );
        cacheManager.setCacheNames(
                List.of(
                        CacheNames.LAUNCH_CACHE,
                        CacheNames.ASTRONAUT_CACHE,
                        CacheNames.LAUNCHER_CACHE,
                        CacheNames.SPACECRAFT_CACHE,
                        CacheNames.PROGRAM_CACHE,
                        CacheNames.AGENCIES_CACHE,
                        CacheNames.PAD_CACHE,
                        CacheNames.ROCKET_CACHE,
                        CacheNames.ROCKET_IMAGES_CACHE,
                        CacheNames.AGENCIES_IMAGES_CACHE,
                        CacheNames.SPACECRAFT_IMAGES_CACHE,
                        CacheNames.PADS_IMAGES_CACHE,
                        CacheNames.LOCATIONS_IMAGES_CACHE,
                        CacheNames.ASTRONAUTS_IMAGES_CACHE,
                        CacheNames.LAUNCHERS_IMAGES_CACHE,
                        CacheNames.MISSION_PATCHES_IMAGES_CACHE,
                        CacheNames.PROGRAMS_IMAGES_CACHE,
                        CacheNames.BOOKMARKS_CACHE,
                        CacheNames.BOOKMARKED_ITEMS_CACHE,
                        CacheNames.CONTACT_MESSAGES_CACHE,
                        CacheNames.MEMBERS_CACHE
                )
        );
        return cacheManager;
    }
}
