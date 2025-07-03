package com.moonkeyeu.core.api.launch.services.impl;

import com.moonkeyeu.core.api.launch.dto.DTOEntity;
import com.moonkeyeu.core.api.launch.dto.agency.AgencyDetailedDTO;
import com.moonkeyeu.core.api.launch.dto.agency.AgencySummarizedDTO;
import com.moonkeyeu.core.api.launch.dto.launch.LaunchNormalDTO;
import com.moonkeyeu.core.api.launch.model.agency.Agencies;
import com.moonkeyeu.core.api.launch.model.launch.Launch;
import com.moonkeyeu.core.api.launch.repository.AgenciesRepository;
import com.moonkeyeu.core.api.launch.repository.LaunchRepository;
import com.moonkeyeu.core.api.launch.services.AgenciesService;
import com.moonkeyeu.core.api.configuration.utils.DtoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AgenciesServiceImpl implements AgenciesService {

    private final AgenciesRepository agenciesRepository;
    private final LaunchRepository launchRepository;
    private final DtoConverter dtoConverter;
    @Autowired
    public AgenciesServiceImpl(DtoConverter dtoConverter,  AgenciesRepository agenciesRepository, LaunchRepository launchRepository) {
        this.dtoConverter = dtoConverter;
        this.agenciesRepository = agenciesRepository;
        this.launchRepository = launchRepository;
    }

    @Cacheable(value = "agencies-cache", sync = true)
    @Override
    public List<DTOEntity> getAllAgencies() {
        List<Agencies> agencies = agenciesRepository.findAll();
        return agencies.stream().map(agency -> dtoConverter.convertToDto(agency, AgencySummarizedDTO.class)).collect(Collectors.toList());
    }

    @Cacheable(value = "agencies-cache", key = "'agency-' + #agencyId", sync = true)
    @Override
    public Optional<DTOEntity> getAgencyById(Integer agencyId) {
        Optional<Agencies> agency = agenciesRepository.findAgenciesById(agencyId);
        Optional<Launch> launch = launchRepository.findUpcomingLaunchesByAgencyId(agencyId);
        return agency.map(org -> {
            AgencyDetailedDTO dto = dtoConverter.convertToDto(org, AgencyDetailedDTO.class);
            launch.ifPresent(l -> {
                LaunchNormalDTO launchDTO = dtoConverter.convertToDto(l, LaunchNormalDTO.class);
                dto.setUpcomingLaunches(launchDTO);
            });
            return dto;
        });
    }
}
