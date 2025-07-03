package com.moonkeyeu.core.api.security.services.cookie;

import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

@Service
public class CookieServiceBuilder {

    public ResponseCookie buildCookie(String tokenType, String token, long maxAge){
        return  ResponseCookie
                .from(tokenType, token)
                .path("/")
                .maxAge(maxAge)
                .secure(true)
                .httpOnly(true)
                .sameSite("Strict")
                .build();
    }
}
