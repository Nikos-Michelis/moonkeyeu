package com.moonkeyeu.core.api.launch.services.impl;

import com.moonkeyeu.core.api.launch.dto.launcher.LauncherDTO;
import com.moonkeyeu.core.api.launch.dto.paging.PageSortingDTO;
import com.moonkeyeu.core.api.launch.model.launcher.Launcher;
import com.moonkeyeu.core.api.launch.dto.DTOEntity;
import com.moonkeyeu.core.api.launch.repository.LauncherRepository;
import com.moonkeyeu.core.api.launch.repository.specifications.LauncherSpecification;
import com.moonkeyeu.core.api.launch.services.LauncherService;
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

@Service
public class LauncherServiceImpl implements LauncherService {
    private final LauncherRepository launcherRepository;
    private final DtoConverter dtoConverter;

    @Autowired
    public LauncherServiceImpl(DtoConverter dtoConverter, LauncherRepository launcherRepository) {
        this.dtoConverter = dtoConverter;
        this.launcherRepository = launcherRepository;
    }


    @Cacheable(value = "launcher-cache",  key = "'launcher-pagination' + #requestParams + #pageSortingDTO", sync = true)
    @Override
    public Page<DTOEntity> searchLauncher(Map<String, String> requestParams, PageSortingDTO pageSortingDTO) {
        Specification<Launcher> spec = Specification.where(null);
        if (requestParams != null && !requestParams.isEmpty()) {
            if (requestParams.containsKey("search")) {
                String searchKey = requestParams.get("search");
                spec = spec.and(LauncherSpecification.hasSearchKey(searchKey));
            }
        }
        Sort  sortObject = "desc".equalsIgnoreCase(pageSortingDTO.getSort())
                ? Sort.by(pageSortingDTO.getField()).descending()
                : Sort.by(pageSortingDTO.getField()).ascending();
        int page = (pageSortingDTO.getPage() > 0) ? pageSortingDTO.getPage() - 1 : 0;
        Pageable pageable = PageRequest.of(page, pageSortingDTO.getLimit(), sortObject);
        Page<Launcher> launchers = launcherRepository.findAll(spec, pageable);
        return launchers.map(launcher -> dtoConverter.convertToDto(launcher, LauncherDTO.class));

    }
}
