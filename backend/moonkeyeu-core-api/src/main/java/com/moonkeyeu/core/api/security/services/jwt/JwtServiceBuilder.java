package com.moonkeyeu.core.api.security.services.jwt;

import com.moonkeyeu.core.api.security.model.token.jwt.Token;
import com.moonkeyeu.core.api.user.model.Roles;
import com.moonkeyeu.core.api.user.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.PrivateKey;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtServiceBuilder {
    @Value("${jwt.issuer}")
    private String issuer;
    @Value("${jwt.audience}")
    private String audience;
    public String buildToken(Token token, User userDetails, PrivateKey privateKey) {
        var authorities = userDetails.getRoles()
                .stream().
                 map(Roles::getName)
                .toList();
        return Jwts.builder()
                .setId(token.getJti())
                .claim("authorities", authorities)
                .claim("scope", token.getTokenScope())
                .setIssuer(issuer)
                .setAudience(audience)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(Date.from(token.getIssuedAt()))
                .setExpiration(Date.from(token.getExpiresAt()))
                .signWith(privateKey, SignatureAlgorithm.RS256)
                .compact();
    }
}
