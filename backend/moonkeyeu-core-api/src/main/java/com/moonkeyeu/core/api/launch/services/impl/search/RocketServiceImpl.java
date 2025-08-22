package com.moonkeyeu.core.api.launch.services.impl.search;

import com.moonkeyeu.core.api.configuration.utils.CacheNames;
import com.moonkeyeu.core.api.launch.dto.paging.PageSortingDTO;
import com.moonkeyeu.core.api.launch.dto.rocket.RocketConfigSummarizedDTO;
import com.moonkeyeu.core.api.launch.dto.rocket.RocketNormalDTO;
import com.moonkeyeu.core.api.launch.model.rocket.Rocket;
import com.moonkeyeu.core.api.launch.model.rocket.RocketConfiguration;
import com.moonkeyeu.core.api.launch.dto.DTOEntity;
import com.moonkeyeu.core.api.launch.repository.RocketsRepository;
import com.moonkeyeu.core.api.launch.repository.specifications.RocketSpecification;
import com.moonkeyeu.core.api.launch.services.RocketService;
import com.moonkeyeu.core.api.configuration.utils.DtoConverter;
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
public class RocketServiceImpl implements RocketService {
    private final RocketsRepository rocketsRepository;
    private final DtoConverter dtoConverter;
    public RocketServiceImpl(RocketsRepository rocketsRepository, DtoConverter dtoConverter) {
        this.rocketsRepository = rocketsRepository;
        this.dtoConverter = dtoConverter;
    }

    @Override
    @Cacheable(value = CacheNames.ROCKET_CACHE,  key = "'rocket-pagination-' + #requestParams + '-' + #pageSortingDTO", sync = true)
    public Page<DTOEntity> searchRocket(Map<String, String> requestParams, PageSortingDTO pageSortingDTO) {
        Specification<RocketConfiguration> spec = Specification.where(null);
        if (requestParams != null && !requestParams.isEmpty()) {

            if (requestParams.containsKey("active")) {
                Boolean isActive = Boolean.valueOf(requestParams.get("active"));
                spec = spec.and(RocketSpecification.isActive(isActive));
            }

            if (requestParams.containsKey("reusable")) {
                Boolean isReusable = Boolean.valueOf(requestParams.get("reusable"));
                spec = spec.and(RocketSpecification.isReusable(isReusable));
            }

            if (requestParams.containsKey("agency")) {
                String agency = requestParams.get("agency");
                spec = spec.and(RocketSpecification.hasAgency(agency));
            }

            if (requestParams.containsKey("search")) {
                String searchKey = requestParams.get("search");
                spec = spec.and(RocketSpecification.hasSearchKey(searchKey));
            }
        }
        Sort  sortObject = "desc".equalsIgnoreCase(pageSortingDTO.getSort())
                ? Sort.by(pageSortingDTO.getField()).descending()
                : Sort.by(pageSortingDTO.getField()).ascending();
        int page = (pageSortingDTO.getPage() > 0) ? pageSortingDTO.getPage() - 1 : 0;
        Pageable pageable = PageRequest.of(page, pageSortingDTO.getLimit(), sortObject);
        Page<RocketConfiguration> rockets = rocketsRepository.findAll(spec, pageable);
        return rockets.map(rocket -> dtoConverter.convertToDto(rocket, RocketConfigSummarizedDTO.class));

    }
    @Override
    @Cacheable(value = CacheNames.ROCKET_CACHE,  key = "'rocket-' + #rocketId", sync = true)
    public Optional<RocketNormalDTO> getRocketById(Integer rocketId) {
        Optional<Rocket> rocket = rocketsRepository.findRocketWithRocketId(rocketId);
        return rocket.map(r -> dtoConverter.convertToDto(r, RocketNormalDTO.class));
    }
}
