package com.moonkeyeu.core.api.launch.services;

import com.moonkeyeu.core.api.launch.dto.celestial.CelestialBodyDTO;

import java.util.List;

public interface CelestialBodiesService {
    List<CelestialBodyDTO> getAllCelestialBodies();
}
