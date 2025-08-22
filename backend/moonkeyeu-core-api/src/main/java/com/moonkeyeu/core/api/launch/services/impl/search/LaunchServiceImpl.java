package com.moonkeyeu.core.api.launch.services.impl.search;

import com.moonkeyeu.core.api.configuration.utils.CacheNames;
import com.moonkeyeu.core.api.launch.dto.launch.LaunchDTO;
import com.moonkeyeu.core.api.launch.dto.launch.LaunchNormalDTO;
import com.moonkeyeu.core.api.launch.dto.paging.PageSortingDTO;
import com.moonkeyeu.core.api.launch.model.launch.Launch;
import com.moonkeyeu.core.api.launch.dto.DTOEntity;
import com.moonkeyeu.core.api.launch.repository.LaunchRepository;
import com.moonkeyeu.core.api.launch.repository.specifications.LaunchSpecification;
import com.moonkeyeu.core.api.launch.services.LaunchService;
import com.moonkeyeu.core.api.configuration.utils.DtoConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class LaunchServiceImpl implements LaunchService {
    private final LaunchRepository launchRepository;
    private final DtoConverter dtoConverter;

    @Autowired
    public LaunchServiceImpl(DtoConverter dtoConverter, LaunchRepository launchRepository) {
        this.dtoConverter = dtoConverter;
        this.launchRepository = launchRepository;
    }

    @Override
    @Cacheable(value = CacheNames.LAUNCH_CACHE,  key = "'launch-pagination-' + #requestParams + '-' + #pageSortingDTO", sync = true)
    public Page<DTOEntity> searchLaunch(Map<String, String> requestParams, PageSortingDTO pageSortingDTO) throws NumberFormatException {
        Specification<Launch> spec = Specification.where(null);
        spec = spec.and(LaunchSpecification.rootSpecification());

        if (requestParams != null && !requestParams.isEmpty()) {
            if (requestParams.containsKey("launcher")) {
                String serialNumber = requestParams.get("launcher");
                spec = spec.and(LaunchSpecification.hasLauncherSerialNumber(serialNumber));
            }

            if (requestParams.containsKey("spacecraftConfig")) {
                String spacecraft = requestParams.get("spacecraftConfig");
                spec = spec.and(LaunchSpecification.hasSpacecraftConfiguration(spacecraft));
            }

            if (requestParams.containsKey("rocketConfig")) {
               String rocketConfiguration = requestParams.get("rocketConfig");
               spec = spec.and(LaunchSpecification.hasRocketConfiguration(rocketConfiguration));
            }

            if (requestParams.containsKey("upcoming")) {
                Boolean upcoming = Boolean.valueOf(requestParams.get("upcoming"));
                spec = spec.and(LaunchSpecification.isUpcomingLaunch(upcoming));
                pageSortingDTO.setSort(upcoming ? "asc" : "desc");
            }

            if (requestParams.containsKey("astronaut")) {
                String astronaut = requestParams.get("astronaut");
                spec = spec.and(LaunchSpecification.hasAstronaut(astronaut));
            }

            if (requestParams.containsKey("agency")) {
                String agency = requestParams.get("agency");
                spec = spec.and(LaunchSpecification.hasAgency(agency));
            }

            if (requestParams.containsKey("location")) {
                String astronaut = requestParams.get("location");
                spec = spec.and(LaunchSpecification.hasLocation(astronaut));
            }

            if (requestParams.containsKey("pad")) {
                String launchPadId = requestParams.get("pad");
                spec = spec.and(LaunchSpecification.hasLaunchPad(launchPadId));
            }

            if (requestParams.containsKey("program")) {
                String program = requestParams.get("program");
                spec = spec.and(LaunchSpecification.hasProgram(program));
            }

            if (requestParams.containsKey("search")) {
                String searchKey = requestParams.get("search");
                spec = spec.and(LaunchSpecification.hasSearchKey(searchKey));
            }

        }
        Sort sortObject = "desc".equalsIgnoreCase(pageSortingDTO.getSort())
                    ? Sort.by(pageSortingDTO.getField()).descending()
                    : Sort.by(pageSortingDTO.getField()).ascending();

        int page = (pageSortingDTO.getPage() > 0) ? pageSortingDTO.getPage() - 1 : 0;
        Pageable pageable = PageRequest.of(page, pageSortingDTO.getLimit(), sortObject);
        Page<Launch> launches = launchRepository.findAll(spec, pageable);
        return launches.map(launch -> dtoConverter.convertToDto(launch, LaunchNormalDTO.class));
    }
    @Override
    @Cacheable(value = CacheNames.LAUNCH_CACHE,  key = "'launch-' + #launchId", sync = true)
    public Optional<DTOEntity> getLaunchById(String launchId) {
        Optional<Launch> launch = launchRepository.findLaunchWithLaunchId(launchId);
        return launch.map(l -> dtoConverter.convertToDto(l, LaunchDTO.class));
    }
}
