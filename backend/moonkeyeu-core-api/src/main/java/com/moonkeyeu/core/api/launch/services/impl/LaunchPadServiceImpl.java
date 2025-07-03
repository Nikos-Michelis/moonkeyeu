package com.moonkeyeu.core.api.launch.services.impl;

import com.moonkeyeu.core.api.launch.dto.pad.LaunchPadDTO;
import com.moonkeyeu.core.api.launch.dto.pad.LaunchPadDetailedDTO;
import com.moonkeyeu.core.api.launch.model.pad.LaunchPad;
import com.moonkeyeu.core.api.launch.dto.DTOEntity;
import com.moonkeyeu.core.api.launch.repository.LaunchPadRepository;
import com.moonkeyeu.core.api.launch.services.LaunchPadService;
import com.moonkeyeu.core.api.configuration.utils.DtoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class LaunchPadServiceImpl implements LaunchPadService {
    private final LaunchPadRepository launchPadRepository;
    private final DtoConverter dtoConverter;
    @Autowired
    public LaunchPadServiceImpl(DtoConverter dtoConverter, LaunchPadRepository launchPadRepository) {
        this.dtoConverter = dtoConverter;
        this.launchPadRepository = launchPadRepository;
    }

    @Cacheable(value = "pad-cache",  key = "'pad'", sync = true)
    @Override
    public Map<String, Object> getAllLaunchPads() {
        List<LaunchPad> launchPads = launchPadRepository.findAll();
        Map<Boolean, Long> counts = launchPads.stream()
                .collect(Collectors.partitioningBy(LaunchPad::isActive, Collectors.counting()));
        int activePads = counts.getOrDefault(true, 0L).intValue();
        int inactivePads = counts.getOrDefault(false, 0L).intValue();
        List<DTOEntity> pads = launchPads
                .stream()
                .map(launchPad -> dtoConverter.convertToDto(launchPad, LaunchPadDTO.class))
                .collect(Collectors.toList());
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("active", activePads);
        map.put("inactive", inactivePads);
        map.put("pads", pads);
        return map;
    }
    @Cacheable(value = "pad-cache",  key = "'pad-' + #launchPadId", sync = true)
    @Override
    public Optional<DTOEntity> getLaunchPadById(Integer launchPadId) {
        Optional<LaunchPad> launchPad = launchPadRepository.findLaunchPadWithPadId(launchPadId);
        return launchPad.map(lp -> dtoConverter.convertToDto(lp, LaunchPadDetailedDTO.class));
    }
}
