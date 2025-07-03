package com.moonkeyeu.core.api.security.services.cookie;

import com.moonkeyeu.core.api.security.dto.response.TokenDetailsDTO;
import com.moonkeyeu.core.api.security.services.jwt.JwtServiceProvider;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CookieServiceProvider {

    private final CookieServiceBuilder cookieServiceBuilder;
    public ResponseCookie buildRefreshTokenCookie(TokenDetailsDTO tokenDetailsDTO) {;
        long maxAge = calculateCookieMaxAge(tokenDetailsDTO.getRefreshTokenExpiresAt().toEpochMilli(),
                tokenDetailsDTO.getRefreshTokenIssuedAt().toEpochMilli());
        return cookieServiceBuilder.buildCookie("refresh_token", tokenDetailsDTO.getRefreshToken(), maxAge);
    }
    public ResponseCookie clearRefreshTokenCookie() {
        return cookieServiceBuilder.buildCookie("refresh_token", null, 0);
    }
    public long calculateCookieMaxAge(long expire, long issue){
        return  ((expire - issue) / 1000);
    }
    public void clearExpiredAuthCookie(HttpServletResponse response){
        SecurityContextHolder.clearContext();
        ResponseCookie refreshTokenCookie = clearRefreshTokenCookie();
        response.addHeader(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString());
    }
    public CookieCsrfTokenRepository buildCsrfCookie() {
        CookieCsrfTokenRepository csrfTokenRepository = CookieCsrfTokenRepository.withHttpOnlyFalse();
        csrfTokenRepository.setCookieCustomizer(cookie -> cookie
                .httpOnly(true)
                .sameSite("Lax")
                .secure(true)
        );
        return csrfTokenRepository;
    }
}
