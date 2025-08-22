package com.moonkeyeu.core.api.launch.services.impl.search;

import com.moonkeyeu.core.api.configuration.utils.CacheNames;
import com.moonkeyeu.core.api.launch.dto.astronaut.AstronautDetailedDTO;
import com.moonkeyeu.core.api.launch.dto.astronaut.AstronautNormalDTO;
import com.moonkeyeu.core.api.launch.dto.paging.PageSortingDTO;
import com.moonkeyeu.core.api.launch.model.astronaut.Astronaut;
import com.moonkeyeu.core.api.launch.dto.DTOEntity;
import com.moonkeyeu.core.api.launch.repository.AstronautsRepository;
import com.moonkeyeu.core.api.launch.repository.specifications.AstronautSpecification;
import com.moonkeyeu.core.api.launch.services.AstronautService;
import com.moonkeyeu.core.api.configuration.utils.DtoConverter;
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
public class AstronautServiceImpl implements AstronautService {
    private final AstronautsRepository astronautsRepository;
    private final DtoConverter dtoConverter;
    @Autowired
    public AstronautServiceImpl(AstronautsRepository astronautsRepository, DtoConverter dtoConverter) {
        this.astronautsRepository = astronautsRepository;
        this.dtoConverter = dtoConverter;
    }

    @Override
    @Cacheable(value = CacheNames.ASTRONAUT_CACHE,  key = "'astronaut-pagination-' + #requestParams + '-' + #pageSortingDTO", sync = true)
    public Page<DTOEntity> searchAstronaut(Map<String, String> requestParams, PageSortingDTO pageSortingDTO) {
        Specification<Astronaut> spec = Specification.where(null);
        if (requestParams != null && !requestParams.isEmpty()) {
            if (requestParams.containsKey("nationality") && !requestParams.get("nationality").isEmpty()) {
                spec = spec.and(AstronautSpecification.hasNationality(requestParams.get("nationality")));
            }
            if (requestParams.containsKey("status") && !requestParams.get("status").isEmpty()) {
                spec = spec.and(AstronautSpecification.hasStatus(requestParams.get("status")));
            }
            if (requestParams.containsKey("agency") && !requestParams.get("agency").isEmpty()) {
                spec = spec.and(AstronautSpecification.hasAgency(requestParams.get("agency")));
            }
            if (requestParams.containsKey("search") && !requestParams.get("search").isEmpty()) {
                spec = spec.and(AstronautSpecification.hasSearchKey(requestParams.get("search")));
            }
        }
        Sort sortObject = "desc".equalsIgnoreCase(pageSortingDTO.getSort())
                ? Sort.by(pageSortingDTO.getField()).descending()
                : Sort.by(pageSortingDTO.getField()).ascending();

        int page = (pageSortingDTO.getPage() > 0) ? pageSortingDTO.getPage() - 1 : 0;
        Pageable pageable = PageRequest.of(page, pageSortingDTO.getLimit(), sortObject);
        Page<Astronaut> astronauts = astronautsRepository.findAll(spec, pageable);
        return astronauts.map(astronaut -> dtoConverter.convertToDto(astronaut, AstronautNormalDTO.class));
    }
    @Override
    @Cacheable(value = CacheNames.ASTRONAUT_CACHE,  key = "'astronaut-' + #astronautId", sync = true)
    public Optional<DTOEntity> getAstronautById(Integer astronautId) {
        Optional<Astronaut> astronaut = astronautsRepository.findAstronautAndLaunchByAstronautId(astronautId);
        return astronaut.map(a -> dtoConverter.convertToDto(a, AstronautDetailedDTO.class));
    }

}
