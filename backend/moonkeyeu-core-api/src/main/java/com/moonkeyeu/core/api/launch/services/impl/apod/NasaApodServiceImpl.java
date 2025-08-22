package com.moonkeyeu.core.api.launch.services.impl.apod;

import com.moonkeyeu.core.api.launch.dto.NasaApodDTO;
import com.moonkeyeu.core.api.launch.services.CacheManagerService;
import com.moonkeyeu.core.api.launch.services.NasaApodClientService;
import com.moonkeyeu.core.api.launch.services.NasaApodService;
import com.moonkeyeu.core.api.settings.exceptions.NasaApodFetchException;
import com.moonkeyeu.core.api.settings.exceptions.RemoteServiceUnavailableException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NasaApodServiceImpl implements NasaApodService {
    private final NasaApodClientService nasaApodClientService;
    private final CacheManagerService cacheManagerService;
    @Value("${application.api.nasa-apod.media.type}")
    private String mediaType;

    @Autowired
    public NasaApodServiceImpl(
            CacheManagerService cacheManagerService,
            NasaApodClientService nasaApodClientService
    ) {
        this.cacheManagerService = cacheManagerService;
        this.nasaApodClientService = nasaApodClientService;
    }

    @Override
    public void refreshNasaApod() {
        Cache nasaApodCache = cacheManagerService.getCacheByName("nasa-apod-cache");
        NasaApodDTO cached = nasaApodCache.get("today", NasaApodDTO.class);
        try {
            NasaApodDTO latest = nasaApodClientService.fetchNasaAstronomyPictureOfTheDay();

            if (!mediaType.equals(latest.getMedia_type())) {
                throw new NasaApodFetchException("Invalid data format: expected 'image' but got '" + latest.getMedia_type() + "'");
            }

            if (cached == null || cached.getDate().isBefore(latest.getDate())) {
                nasaApodCache.put("today", latest);
                log.info("Successfully prepare nasa-apod cache.");
            }
        } catch (NasaApodFetchException e){
            if (cached != null) {
                log.info("Fetch failed, keeping cached NASA APOD from " + cached.getDate());
            }
            log.error("Fetch failed: {}", e.getMessage());
        }
    }
    @Override
    public NasaApodDTO getNasaApodFromCache() throws RemoteServiceUnavailableException {
        return cacheManagerService.getByCacheNameAndKey("nasa-apod-cache", "today", NasaApodDTO.class)
                .orElseThrow(() -> new RemoteServiceUnavailableException("Nasa Apod temporary unavailable, try again later."));
    }
}