package com.moonkeyeu.core.api.launch.services;

import com.moonkeyeu.core.api.launch.dto.NasaApodDTO;

public interface NasaApodService {
    void refreshNasaApod();
    NasaApodDTO getNasaApodFromCache();
}