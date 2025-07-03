package com.moonkeyeu.core.api.security.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenDetailsDTO {
    private String jti;
    private String accessToken;
    private String refreshToken;
    private Instant accessTokenIssuedAt;
    private Instant refreshTokenIssuedAt;
    private Instant accessTokenExpiresAt;
    private Instant refreshTokenExpiresAt;
    private String message;
}
