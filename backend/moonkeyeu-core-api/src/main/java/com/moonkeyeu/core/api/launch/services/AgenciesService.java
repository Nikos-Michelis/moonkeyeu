package com.moonkeyeu.core.api.launch.services;

import com.moonkeyeu.core.api.launch.dto.DTOEntity;
import com.moonkeyeu.core.api.launch.dto.paging.PageSortingDTO;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface AgenciesService {
    Optional<DTOEntity> getAgencyById(Integer agencyId);
    List<DTOEntity> getAllAgencies();
}
