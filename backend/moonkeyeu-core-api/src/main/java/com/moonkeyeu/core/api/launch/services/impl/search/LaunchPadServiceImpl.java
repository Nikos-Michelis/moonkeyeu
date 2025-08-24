package com.moonkeyeu.core.api.launch.services.impl.search;

import com.moonkeyeu.core.api.configuration.utils.CacheNames;
import com.moonkeyeu.core.api.launch.dto.launch.LaunchNormalDTO;
import com.moonkeyeu.core.api.launch.dto.pad.LaunchPadDTO;
import com.moonkeyeu.core.api.launch.dto.pad.LaunchPadDetailedDTO;
import com.moonkeyeu.core.api.launch.dto.program.ProgramDetailedDTO;
import com.moonkeyeu.core.api.launch.model.launch.Launch;
import com.moonkeyeu.core.api.launch.model.pad.LaunchPad;
import com.moonkeyeu.core.api.launch.dto.DTOEntity;
import com.moonkeyeu.core.api.launch.repository.LaunchPadRepository;
import com.moonkeyeu.core.api.launch.repository.LaunchRepository;
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
    private final LaunchRepository launchRepository;
    private final DtoConverter dtoConverter;
    @Autowired
    public LaunchPadServiceImpl(DtoConverter dtoConverter, LaunchPadRepository launchPadRepository, LaunchRepository launchRepository) {
        this.dtoConverter = dtoConverter;
        this.launchPadRepository = launchPadRepository;
        this.launchRepository = launchRepository;
    }

    @Override
    @Cacheable(value = CacheNames.PAD_CACHE,  key = "'pads'", sync = true)
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
    @Override
    @Cacheable(value = CacheNames.PAD_CACHE,  key = "'pad-' + #launchPadId", sync = true)
    public Optional<DTOEntity> getLaunchPadById(Integer launchPadId) {
        Optional<LaunchPad> launchPad = launchPadRepository.findLaunchPadWithPadId(launchPadId);
        Optional<Launch> launch = launchRepository.findUpcomingLaunchesByLaunchPadId(launchPadId);
        return launchPad.map(org -> {
            LaunchPadDetailedDTO dto = dtoConverter.convertToDto(org, LaunchPadDetailedDTO.class);
            launch.ifPresent(l -> {
                LaunchNormalDTO launchDTO = dtoConverter.convertToDto(l, LaunchNormalDTO.class);
                dto.setUpcomingLaunches(launchDTO);
            });
            return dto;
        });
    }
}
