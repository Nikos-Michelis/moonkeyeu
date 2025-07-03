package com.moonkeyeu.core.api.launch.services;

import com.moonkeyeu.core.api.launch.dto.DTOEntity;
import com.moonkeyeu.core.api.launch.dto.paging.PageSortingDTO;
import com.moonkeyeu.core.api.launch.dto.rocket.RocketNormalDTO;
import org.springframework.data.domain.Page;

import java.util.Map;
import java.util.Optional;

public interface RocketService {
    Optional<RocketNormalDTO> getRocketById(Integer rocketId);
    Page<DTOEntity> searchRocket(Map<String, String> requestParams, PageSortingDTO pageSortingDTO);

}
