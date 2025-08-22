package com.moonkeyeu.core.api.launch.services.impl.search;

import com.moonkeyeu.core.api.configuration.utils.CacheNames;
import com.moonkeyeu.core.api.launch.dto.paging.PageSortingDTO;
import com.moonkeyeu.core.api.launch.dto.spacecraft.SpacecraftConfigSummarizedDTO;
import com.moonkeyeu.core.api.launch.dto.spacecraft.SpacecraftConfigurationDTO;
import com.moonkeyeu.core.api.launch.model.spacecraft.SpacecraftConfiguration;
import com.moonkeyeu.core.api.launch.dto.DTOEntity;
import com.moonkeyeu.core.api.launch.repository.SpacecraftRepository;
import com.moonkeyeu.core.api.launch.repository.specifications.SpacecraftConfigSpecification;
import com.moonkeyeu.core.api.launch.services.SpacecraftService;
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
public class SpacecraftServiceImpl implements SpacecraftService {

    private final SpacecraftRepository spacecraftRepository;
    private final DtoConverter dtoConverter;

    @Autowired
    public SpacecraftServiceImpl(DtoConverter dtoConverter, SpacecraftRepository spacecraftRepository) {
        this.dtoConverter = dtoConverter;
        this.spacecraftRepository = spacecraftRepository;
    }

    @Override
    @Cacheable(value = CacheNames.SPACECRAFT_CACHE,  key = "'spacecraft-pagination-' + #requestParams + '-' + #pageSortingDTO", sync = true)
    public Page<DTOEntity> searchSpacecraft(Map<String, String> requestParams, PageSortingDTO pageSortingDTO) {
        Specification<SpacecraftConfiguration> spec = Specification.where(null);
        if (requestParams != null && !requestParams.isEmpty()) {
            if (requestParams.containsKey("search")) {
                String searchKey = requestParams.get("search");
                spec = spec.and(SpacecraftConfigSpecification.hasSearchKey(searchKey));
            }
        }
        Sort sortObject = "desc".equalsIgnoreCase(pageSortingDTO.getSort())
                ? Sort.by(pageSortingDTO.getField()).descending()
                : Sort.by(pageSortingDTO.getField()).ascending();

        int page = (pageSortingDTO.getPage() > 0) ? pageSortingDTO.getPage() - 1 : 0;
        Pageable pageable = PageRequest.of(page, pageSortingDTO.getLimit(), sortObject);
        Page<SpacecraftConfiguration> spacecrafts = spacecraftRepository.findAll(spec, pageable);
        return spacecrafts.map(spacecraft -> dtoConverter.convertToDto(spacecraft, SpacecraftConfigSummarizedDTO.class));
    }
    @Cacheable(value = CacheNames.SPACECRAFT_CACHE,  key = "'spacecrat-' + #spacecraftId", sync = true)
    public Optional<SpacecraftConfigurationDTO> getSpacecraftById(Integer spacecraftId) {
        Optional<SpacecraftConfiguration> spacecraft = spacecraftRepository.findSpacecraftWithSpacecraftId(spacecraftId);
        return spacecraft.map(s -> dtoConverter.convertToDto(s, SpacecraftConfigurationDTO.class));
    }
}
