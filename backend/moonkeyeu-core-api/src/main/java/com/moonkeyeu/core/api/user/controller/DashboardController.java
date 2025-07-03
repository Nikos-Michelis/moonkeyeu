package com.moonkeyeu.core.api.user.controller;

import com.moonkeyeu.core.api.launch.dto.DTOEntity;
import com.moonkeyeu.core.api.launch.dto.paging.PageSortingDTO;
import com.moonkeyeu.core.api.security.limiter.RateLimited;
import com.moonkeyeu.core.api.user.dto.ContactDTO;
import com.moonkeyeu.core.api.user.dto.response.ResponseDTO;
import com.moonkeyeu.core.api.user.services.BatchService;
import com.moonkeyeu.core.api.user.services.DashboardService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Map;

@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ROLE_DEVELOPER', 'ROLE_ADMIN', 'ROLE_MODERATOR')")
@Tag(name = "Dashboard")
public class DashboardController {
    private final DashboardService dashboardService;
    private final BatchService batchService;
    private final int MAX_ITEMS = 50;


    @GetMapping("/contact/messages")
    @PreAuthorize("hasAnyAuthority('admin:read', 'moderator:read')")
    @RateLimited(requests = 100, durationSeconds = 60)
    public ResponseEntity<?> getContactMessages(
            @RequestParam(required = false) Map<String, String> requestParams,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "12") Integer limit,
            @RequestParam(defaultValue = "createdAt") String field,
            @RequestParam(defaultValue = "desc") String ordering,
            PagedResourcesAssembler<DTOEntity> assembler
    ) {
        return ResponseEntity
                .ok(assembler.toModel(dashboardService.searchMessages(requestParams,
                        PageSortingDTO.builder()
                                .page(page)
                                .limit(limit >= MAX_ITEMS ? MAX_ITEMS : limit)
                                .field(field)
                                .sort(ordering)
                                .build())));
    }
    @DeleteMapping("/contact/messages/delete/{messageId}")
    @PreAuthorize("hasAuthority('admin:delete')")
    @RateLimited(requests = 100, durationSeconds = 60)
    public ResponseEntity<?> deleteMessage(
            @Valid @PathVariable
            @Positive(message = "Message identifier should be greater than zero.")
            @NotNull(message = "Message identifier is required") Long messageId
    ) {
        ContactDTO contactDTO = dashboardService.deleteMessage(messageId);
        return ResponseEntity.ok()
                .body(ResponseDTO.builder()
                        .timestamp(Instant.now())
                        .message("Message deleted successfully.")
                        .data(contactDTO)
                        .build());
    }

    @GetMapping("/report/tasks")
    @PreAuthorize("hasAnyAuthority('developer:read', 'admin:read', 'moderator:read')")
    @RateLimited(requests = 100, durationSeconds = 60)
    public ResponseEntity<?> getBatchJobExecutions() {
        return ResponseEntity.ok().
                body(ResponseDTO
                        .builder()
                        .timestamp(Instant.now())
                        .data(batchService.getAllBatchJobs())
                        .build());
    }

    @GetMapping("/report/members")
    @PreAuthorize("hasAnyAuthority('developer:read', 'admin:read', 'moderator:read')")
    @RateLimited(requests = 100, durationSeconds = 60)
    public ResponseEntity<?> getAllMembers() {
        return ResponseEntity.ok().
                body(ResponseDTO
                        .builder()
                        .timestamp(Instant.now())
                        .data(dashboardService.getAllMembers())
                        .build());
    }
}
