package com.moonkeyeu.core.api.security.services;

import com.moonkeyeu.core.api.security.model.token.jwt.Token;
import com.moonkeyeu.core.api.security.services.cookie.CookieServiceProvider;
import com.moonkeyeu.core.api.security.services.jwt.JwtRequestExtractor;
import com.moonkeyeu.core.api.security.repository.TokenRepository;
import com.moonkeyeu.core.api.settings.exceptions.SessionExpiredException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {
    private final JwtRequestExtractor jwtRequestExtractor;
    private final TokenRepository tokenRepository;
    private final CookieServiceProvider cookieServiceProvider;
    @Override
    public void logout(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws SessionExpiredException {
        String token = jwtRequestExtractor.extractToken(request)
                .orElseThrow(() -> new SessionExpiredException("Your session has expired."));
        Optional<Token> storedToken = tokenRepository.findByToken(token);
        if (storedToken.isEmpty()) {
            cookieServiceProvider.clearExpiredAuthCookie(response);
            throw new SessionExpiredException("Your session has expired.");
        }
        Token tokenEntity = storedToken.get();
        tokenEntity.setExpired(true);
        tokenEntity.setRevoked(true);
        tokenRepository.save(tokenEntity);
        SecurityContextHolder.clearContext();
        ResponseCookie refreshTokenCookie = cookieServiceProvider.clearRefreshTokenCookie();
        response.addHeader(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString());
    }
}
