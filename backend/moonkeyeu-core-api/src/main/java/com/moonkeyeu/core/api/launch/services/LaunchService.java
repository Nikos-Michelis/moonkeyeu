package com.moonkeyeu.core.api.launch.services;

import com.moonkeyeu.core.api.launch.dto.DTOEntity;
import com.moonkeyeu.core.api.launch.dto.paging.PageSortingDTO;
import org.springframework.data.domain.Page;

import java.util.Map;
import java.util.Optional;

public interface LaunchService {

    Page<DTOEntity> searchLaunch(Map<String, String> requestParams, PageSortingDTO pageSortingDTO);
    Optional<DTOEntity> getLaunchById(String launchId);
}
