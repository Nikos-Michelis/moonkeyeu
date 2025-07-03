package com.moonkeyeu.core.api.security.dto;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.moonkeyeu.core.api.security.dto.response.TokenDetailsDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OAuth2GoogleDTO {
    private GoogleIdToken.Payload googleTokenPayload;
    private TokenDetailsDTO tokenDetails;
}
