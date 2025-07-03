package com.moonkeyeu.core.api.security.dto;

import com.moonkeyeu.core.api.security.model.token.jwt.TokenScope;
import com.moonkeyeu.core.api.security.model.token.jwt.TokenType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.time.Instant;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TokenDTO {
    private String jti;
    private String token;
    private TokenType tokenType;
    private boolean expired;
    private boolean revoked;
    private Instant expiresAt;
    private Instant issuedAt;
    private TokenScope tokenScope;
    private Long userId;
    private Set<GrantedAuthority> roles;
    private String userName;
}
