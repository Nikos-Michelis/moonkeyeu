package com.moonkeyeu.core.api.security.controller;

import com.moonkeyeu.core.api.security.dto.OAuth2GoogleDTO;
import com.moonkeyeu.core.api.security.dto.request.OAuth2Request;
import com.moonkeyeu.core.api.security.dto.response.ResponseDTO;
import com.moonkeyeu.core.api.security.dto.response.TokenDetailsDTO;
import com.moonkeyeu.core.api.security.services.AuthenticationService;
import com.moonkeyeu.core.api.security.services.cookie.CookieServiceProvider;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.time.Instant;
import java.util.Map;

@RestController
@RequestMapping("oauth2")
@RequiredArgsConstructor
@Tag(name = "OAuth2")
public class OAuth2Controller {

    private final CookieServiceProvider cookieServiceProvider;
    private final AuthenticationService authenticationService;

    @PostMapping("/login/google")
    public ResponseEntity<?> googleLogin(@RequestBody Map<String, String> authMap) {
        OAuth2GoogleDTO result = authenticationService.OAuth2GoogleLogin(authMap);
        ResponseEntity.BodyBuilder responseBuilder = ResponseEntity.ok();
        if (result.getTokenDetails() != null) {
            ResponseCookie refreshTokenCookie = cookieServiceProvider.buildRefreshTokenCookie(result.getTokenDetails());
            responseBuilder.header(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString());
            return responseBuilder.body(
                    ResponseDTO.builder()
                            .timestamp(Instant.now())
                            .token(result.getTokenDetails().getAccessToken())
                            .expiredAt(result.getTokenDetails().getAccessTokenExpiresAt())
                            .message("You have successfully authenticated your account.")
                            .build()
            );
        } else if (result.getGoogleTokenPayload() != null) {
            return responseBuilder.body(result.getGoogleTokenPayload());
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed");
        }
    }


    @PostMapping("/register/google")
    public ResponseEntity<?> googleRegister(@RequestBody @Valid OAuth2Request oAuth2Request) {
        TokenDetailsDTO tokenDetailsDTO = authenticationService.OAuth2GoogleRegister(oAuth2Request);
        ResponseCookie refreshTokenCookie = cookieServiceProvider.buildRefreshTokenCookie(tokenDetailsDTO);
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString())
                .body(ResponseDTO.builder()
                        .timestamp(Instant.now())
                        .token(tokenDetailsDTO.getAccessToken())
                        .expiredAt(tokenDetailsDTO.getAccessTokenExpiresAt())
                        .message("You have successfully authenticate your account.")
                        .build());
    }
}
