package com.moonkeyeu.core.api.user.services;

import com.moonkeyeu.core.api.launch.dto.DTOEntity;
import com.moonkeyeu.core.api.launch.dto.paging.PageSortingDTO;
import com.moonkeyeu.core.api.user.dto.ContactDTO;
import com.moonkeyeu.core.api.user.dto.UserDTO;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface DashboardService {
    Page<DTOEntity> searchMessages(Map<String, String> requestParams, PageSortingDTO pageSortingDTO);
    ContactDTO deleteMessage(Long messageId);
    List<UserDTO> getAllMembers();
}
