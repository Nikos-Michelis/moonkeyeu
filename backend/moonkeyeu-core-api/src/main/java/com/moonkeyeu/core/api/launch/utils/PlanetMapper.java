package com.moonkeyeu.core.api.launch.utils;

import com.moonkeyeu.core.api.launch.dto.celestial.CelestialBodyDTO;
import com.moonkeyeu.core.api.launch.model.CelestialBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PlanetMapper {

    private final PlanetFormatter formatter;

    @Autowired
    public PlanetMapper (PlanetFormatter formatter) {
        this.formatter = formatter;
    }

    public CelestialBodyDTO toResponse(CelestialBody planet) {
        return CelestialBodyDTO.builder()
                .id(planet.getId())
                .name(planet.getName())
                .typeName(planet.getType().getTypeName())
                .mass(formatter.mass(planet.getMass()))
                .diameter(formatter.diameter(planet.getDiameter()))
                .gravity(formatter.gravity(planet.getGravity()))
                .tilt(formatter.tilt(planet.getTilt()))
                .rotation(formatter.rotation(planet.getRotation()))
                .orbit(formatter.orbit(planet.getOrbit()))
                .distance(formatter.distance(planet.getDistance()))
                .moons(planet.getMoons())
                .atmosphere(planet.getAtmosphere())
                .description(planet.getDescription())
                .imgDay(planet.getImgDay())
                .imgNight(planet.getImgNight())
                .imgAtmosphere(planet.getImgAtmosphere())
                .imgRing(planet.getImgRing())
                .build();
    }
}
