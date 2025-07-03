package com.moonkeyeu.core.api.security.services.jwt;

import com.moonkeyeu.core.api.security.dto.TokenDTO;
import com.moonkeyeu.core.api.security.model.token.jwt.TokenScope;
import com.moonkeyeu.core.api.settings.exceptions.InvalidJwtTokenException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.security.PrivateKey;
import java.security.interfaces.RSAPrivateKey;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JwtServiceParserImpl {
    private final RSAPrivateKey accessTokenPrivateKey;
    private final RSAPrivateKey refreshTokenPrivateKey;

    public TokenDTO parseToken(String token) throws InvalidJwtTokenException {
        try {
            return TokenDTO.builder()
                    .token(token)
                    .userName(extractUsernameFromToken(token))
                    .roles(extractRolesFromToken(token))
                    .tokenScope(TokenScope.valueOf(extractScopeFromToken(token)))
                    .issuedAt(extractIssuedDateFromToken(token))
                    .expiresAt(extractExpirationFromToken(token))
                    .build();
        } catch (UnsupportedJwtException | MalformedJwtException | IllegalArgumentException e) {
            throw new InvalidJwtTokenException("Invalid (JWT) token format", e);
        } catch (ExpiredJwtException e) {
            throw new InvalidJwtTokenException("Expired (JWT) token", e);
        } catch (InvalidClaimException e) {
            throw new InvalidJwtTokenException("Invalid value for claim \"" + e.getClaimName() + "\"", e);
        } catch (SignatureException e) {
            throw new InvalidJwtTokenException("Invalid token signature.", e);
        } catch (Exception e) {
            e.printStackTrace();
            throw new InvalidJwtTokenException("Invalid token.", e);
        }
    }

    private Claims extractClaim(String token, PrivateKey privateKey){
        return Jwts.parserBuilder()
                .setSigningKey(privateKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Claims extractAllClaims(String token) {
        try {
            return extractClaim(token, accessTokenPrivateKey);
        } catch (SignatureException e) {
            return extractClaim(token, refreshTokenPrivateKey);
        }
    }

    private String extractTokenIdFromToken(@NotNull String token) {
        return extractAllClaims(token).getId();
    }

    private String extractUsernameFromToken(@NotNull String token) {
        return extractAllClaims(token).getSubject();
    }

    private Instant extractIssuedDateFromToken(@NotNull String token) {
        return extractAllClaims(token).getIssuedAt().toInstant();
    }

    private Instant extractExpirationFromToken(@NotNull String token) {
        return extractAllClaims(token).getExpiration().toInstant();
    }

    private Set<GrantedAuthority> extractRolesFromToken(@NotNull String token) {
        Claims claims = extractAllClaims(token);
        List<String> rolesAsString = claims.get("authorities", List.class);
        if (rolesAsString == null) {
            rolesAsString = new ArrayList<>();
        }
        return rolesAsString.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
    }

    private String extractScopeFromToken(@NotNull String token) {
        Claims claims = extractAllClaims(token);
        return claims.get("scope", String.class);
    }
}
