package com.moonkeyeu.core.api.security.services.jwt;

import com.moonkeyeu.core.api.security.dto.TokenDTO;
import com.moonkeyeu.core.api.security.model.token.jwt.Token;
import com.moonkeyeu.core.api.security.model.token.jwt.TokenScope;
import com.moonkeyeu.core.api.security.model.token.jwt.TokenType;
import com.moonkeyeu.core.api.security.util.RequestTokenBuilder;
import com.moonkeyeu.core.api.user.model.User;
import com.moonkeyeu.core.api.settings.exceptions.InvalidJwtTokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Instant;

@Service
@RequiredArgsConstructor
@Slf4j
public class JwtServiceProvider {
    private final RSAPrivateKey accessTokenPrivateKey;
    private final RSAPublicKey accessTokenPublicKey;
    private final RSAPrivateKey refreshTokenPrivateKey;
    private final RSAPublicKey refreshTokenPublicKey;
    private final JwtServiceBuilder jwtServiceBuilder;
    private final JwtServiceParserImpl jwtServiceParserImpl;
    private final RequestTokenBuilder requestTokenBuilder;

    private static final long ACCESS_EXPIRATION = 15 * 60 * 1000; // 15 minutes
    //private static final long ACCESS_EXPIRATION = 30 * 1000; // 30 seconds
    private static final long REFRESH_EXPIRATION = 3 * 60 * 60 * 1000; // 3 hours
    private static final long REFRESH_EXPIRATION_REMEMBER_ME = 31L * 24 * 60 * 60 * 1000; // 31 days

    public Token generateAccessToken(User user) {
        Instant issuedDate = Instant.now();
        Instant expirationDate = calculateExpirationDate(issuedDate);
        Token token = Token.builder()
                .jti(generateTokenIdentifier())
                .issuedAt(issuedDate)
                .expiresAt(expirationDate)
                .tokenType(TokenType.BEARER)
                .tokenScope(TokenScope.valueOf(TokenScope.ACCESS.name()))
                .user(user)
                .expired(false)
                .revoked(false)
                .build();
        String generatedToken = jwtServiceBuilder.buildToken(token, user ,accessTokenPrivateKey);
        token.setToken(generatedToken);
        return token;
    }

    public Token generateRefreshToken(User user, boolean isRememberMe) {
        Instant issuedDate = Instant.now();
        Instant expirationDate = calculateRefreshTokenExpirationDate(issuedDate, isRememberMe);
        Token token = Token.builder()
                .jti(generateTokenIdentifier())
                .issuedAt(issuedDate)
                .expiresAt(expirationDate)
                .tokenType(TokenType.BEARER)
                .tokenScope(TokenScope.valueOf(TokenScope.REFRESH.name()))
                .user(user)
                .expired(false)
                .revoked(false)
                .build();
        String generatedToken = jwtServiceBuilder.buildToken(token, user, refreshTokenPrivateKey);
        token.setToken(generatedToken);
        return token;
    }

    public boolean isValidToken(String token, UserDetails userDetails) throws InvalidJwtTokenException {
        final TokenDTO tokenDTO = jwtServiceParserImpl.parseToken(token);
        return userDetails.getUsername().equals(tokenDTO.getUserName()) && !isTokenExpired(token);
    }
    private boolean isTokenExpired(@NotNull String token) throws InvalidJwtTokenException {
        return jwtServiceParserImpl.parseToken(token).getExpiresAt().isBefore(Instant.now());
    }

    public boolean isAccessToken(String token, TokenScope tokenScope){
        return tokenScope.equals(TokenScope.ACCESS) && verifyAccessToken(token);
    }

    public boolean isRefreshToken(String token, TokenScope tokenScope){
        return tokenScope.equals(TokenScope.REFRESH) && verifyRefreshToken(token);
    }

    private boolean verifyAccessToken(String token) {
        return verifyJWT(token, accessTokenPublicKey, String.valueOf(TokenScope.ACCESS).toUpperCase());
    }
    private boolean verifyRefreshToken(String token) {
        return verifyJWT(token, refreshTokenPublicKey, String.valueOf(TokenScope.REFRESH).toUpperCase());
    }

    private boolean verifyJWT(String token, PublicKey publicKey, String expectedType) {
        try {
            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(publicKey)
                    .build()
                    .parseClaimsJws(token);

            Claims claims = claimsJws.getBody();
            String tokenType = claims.get("scope", String.class);
            //log.info("token scope -> " + tokenType);
            return expectedType.equals(tokenType);
        } catch (JwtException e) {
            return false;
        }
    }
    private Instant calculateExpirationDate(Instant issuedDate) {
        return issuedDate.plusMillis(JwtServiceProvider.ACCESS_EXPIRATION);
    }

    private Instant calculateRefreshTokenExpirationDate(Instant issuedDate, boolean isRememberMe) {
        long expirationMillis = isRememberMe ? REFRESH_EXPIRATION_REMEMBER_ME : REFRESH_EXPIRATION;
        return issuedDate.plusMillis(expirationMillis);
    }

    private String generateTokenIdentifier() {
        return requestTokenBuilder.generateToken();
    }

}
