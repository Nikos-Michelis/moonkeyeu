package com.moonkeyeu.core.api.launch.services;

import com.moonkeyeu.core.api.launch.dto.DTOEntity;
import com.moonkeyeu.core.api.launch.dto.paging.PageSortingDTO;
import com.moonkeyeu.core.api.launch.dto.spacecraft.SpacecraftConfigurationDTO;
import org.springframework.data.domain.Page;

import java.util.Map;
import java.util.Optional;

public interface SpacecraftService {
    Optional<SpacecraftConfigurationDTO> getSpacecraftById(Integer spacecraftId);
    Page<DTOEntity> searchSpacecraft(Map<String, String> requestParams, PageSortingDTO pageSortingDTO);
}
