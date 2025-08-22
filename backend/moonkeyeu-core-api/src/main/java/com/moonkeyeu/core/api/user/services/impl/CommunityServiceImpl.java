package com.moonkeyeu.core.api.user.services.impl;

import com.moonkeyeu.core.api.user.dto.request.ContactRequest;
import com.moonkeyeu.core.api.user.model.Category;
import com.moonkeyeu.core.api.user.model.Contact;
import com.moonkeyeu.core.api.user.reporitory.ContactRepository;
import com.moonkeyeu.core.api.user.services.CommunityService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommunityServiceImpl implements CommunityService {
    private final ContactRepository contactRepository;
    @Override
    @CacheEvict(value = "contact-messages-cache", allEntries = true)
    public void saveContactForm(ContactRequest contactRequest) {
        contactRepository.save(
                Contact.builder()
                        .email(contactRequest.getEmail())
                        .category(Category.valueOf(contactRequest.getCategory().toUpperCase()))
                        .message(contactRequest.getMessage())
                        .build());

    }
}
