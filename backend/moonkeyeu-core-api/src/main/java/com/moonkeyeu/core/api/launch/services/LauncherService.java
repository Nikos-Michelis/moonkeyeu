package com.moonkeyeu.core.api.launch.services;

import com.moonkeyeu.core.api.launch.dto.DTOEntity;
import com.moonkeyeu.core.api.launch.dto.paging.PageSortingDTO;
import org.springframework.data.domain.Page;

import java.util.Map;

public interface LauncherService {
    //Optional<DTOEntity> getLauncherById(Integer launcherId);
    Page<DTOEntity> searchLauncher(Map<String, String> requestParams, PageSortingDTO pageSortingDTO);

}
