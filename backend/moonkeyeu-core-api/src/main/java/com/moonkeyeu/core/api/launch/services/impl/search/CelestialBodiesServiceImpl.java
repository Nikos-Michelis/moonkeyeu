package com.moonkeyeu.core.api.launch.services.impl.search;

import com.moonkeyeu.core.api.launch.dto.celestial.CelestialBodyDTO;
import com.moonkeyeu.core.api.launch.model.CelestialBody;
import com.moonkeyeu.core.api.launch.repository.CelestialBodiesRepository;
import com.moonkeyeu.core.api.launch.services.CelestialBodiesService;
import com.moonkeyeu.core.api.launch.utils.PlanetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CelestialBodiesServiceImpl implements CelestialBodiesService {
    private final CelestialBodiesRepository celestialBodiesRepository;
    private final PlanetMapper planetMapper;

    @Autowired
    public CelestialBodiesServiceImpl(CelestialBodiesRepository celestialBodiesRepository, PlanetMapper planetMapper) {
        this.celestialBodiesRepository = celestialBodiesRepository;
        this.planetMapper = planetMapper;
    }

    @Override
    public List<CelestialBodyDTO> getAllCelestialBodies() {
        List<CelestialBody> celestialBodies = celestialBodiesRepository.findAll();
        return celestialBodies.stream().map(planetMapper::toResponse).toList();
    }


}
