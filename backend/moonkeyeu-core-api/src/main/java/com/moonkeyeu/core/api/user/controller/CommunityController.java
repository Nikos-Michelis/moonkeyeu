package com.moonkeyeu.core.api.user.controller;

import com.moonkeyeu.core.api.security.limiter.RateLimited;
import com.moonkeyeu.core.api.user.dto.request.ContactRequest;
import com.moonkeyeu.core.api.user.services.CommunityService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/community")
@RequiredArgsConstructor
@Tag(name = "community")
public class CommunityController {

    private final CommunityService communityService;
    @PostMapping("/contact")
    @RateLimited(requests = 5, durationSeconds = 60)
    public ResponseEntity<?> submitContactForm(@RequestBody ContactRequest contactRequest) {
        communityService.saveContactForm(contactRequest);
        return ResponseEntity.ok("Your contact message has been received!");
    }

}
