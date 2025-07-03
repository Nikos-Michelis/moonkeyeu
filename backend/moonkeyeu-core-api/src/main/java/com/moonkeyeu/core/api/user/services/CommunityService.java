package com.moonkeyeu.core.api.user.services;

import com.moonkeyeu.core.api.user.dto.request.ContactRequest;

public interface CommunityService {
    void saveContactForm(ContactRequest contactRequest);
}
