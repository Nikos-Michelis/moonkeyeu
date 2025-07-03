package com.moonkeyeu.core.api.security.controller;

import com.moonkeyeu.core.api.security.dto.request.*;
import com.moonkeyeu.core.api.security.dto.response.ResponseDTO;
import com.moonkeyeu.core.api.security.dto.response.TokenDetailsDTO;
import com.moonkeyeu.core.api.security.limiter.RateLimited;
import com.moonkeyeu.core.api.security.model.otp.OtpToken;
import com.moonkeyeu.core.api.security.services.AuthenticationService;
import com.moonkeyeu.core.api.security.services.cookie.CookieServiceProvider;
import com.moonkeyeu.core.api.security.services.otp.OtpResendService;
import com.moonkeyeu.core.api.user.services.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
@Tag(name = "Authentication")
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final UserService userService;
    private final OtpResendService otpResendService;
    private final CookieServiceProvider cookieServiceProvider;

    @PostMapping("/register")
    @RateLimited(requests = 5, durationSeconds = 60)
    public ResponseEntity<?> register(@RequestBody @Valid RegisterRequest request) throws MessagingException {
        OtpToken otpToken = authenticationService.register(request);
        return ResponseEntity.ok().body(
                ResponseDTO.builder()
                        .timestamp(Instant.now())
                        .token(otpToken.getToken())
                        .expiredAt(otpToken.getExpiresAt())
                        .message("We have just sent the one-time password (OTP) to your email.")
                        .build()
        );
    }

    @PostMapping("/authenticate")
    @RateLimited(requests = 5, durationSeconds = 60)
    public ResponseEntity<?> authenticate(@RequestBody @Valid AuthenticationRequest request) throws MessagingException {
        OtpToken otpToken = authenticationService.authenticate(request);
        return ResponseEntity.ok().body(
                ResponseDTO.builder()
                        .timestamp(Instant.now())
                        .token(otpToken.getToken())
                        .expiredAt(otpToken.getExpiresAt())
                        .message("We have just sent the one-time password (OTP) to your email.")
                        .build()
        );
    }
    @PostMapping("/verify-otp")
    @RateLimited(requests = 5, durationSeconds = 60)
    public ResponseEntity<?> verifyOtp(@RequestBody @Valid OtpValidationRequest otpValidationRequest) {
        TokenDetailsDTO tokenDetailsDTO = authenticationService.VerifyOtp(otpValidationRequest);
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

    @PostMapping("/resend-otp")
    @RateLimited(requests = 5, durationSeconds = 60)
    public ResponseEntity<?> resendOtp(@RequestBody @Valid OtpResendRequest otpResendRequest) throws MessagingException {
        OtpToken otpToken = otpResendService.resendOtp(otpResendRequest);
        return ResponseEntity.ok()
                .body(ResponseDTO.builder()
                        .timestamp(Instant.now())
                        .token(otpToken.getToken())
                        .expiredAt(otpToken.getExpiresAt())
                        .message("We have just resend the one-time password (OTP) to your email.")
                        .build());
    }

    @PostMapping("/forgot-password")
    @RateLimited(requests = 5, durationSeconds = 60)
    public ResponseEntity<?> forgotPassword(@RequestBody @Valid ResetCredentialsRequest resetCredentialsRequest) throws MessagingException {
        userService.generatePasswordResetToken(resetCredentialsRequest);
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/reset-password")
    @RateLimited(requests = 5, durationSeconds = 60)
    public ResponseEntity<?> resetPassword(@RequestBody @Valid ResetPasswordRequest resetPasswordRequest) {
            userService.resetPassword(resetPasswordRequest);
            return ResponseEntity.ok()
                    .body(ResponseDTO.builder()
                    .timestamp(Instant.now())
                    .message("Password successfully changed.")
                    .build());
    }
    @PostMapping("/refresh-token")
    @RateLimited(requests = 100, durationSeconds = 60)
    public ResponseEntity<?> refreshToken(HttpServletRequest request, HttpServletResponse response) {
        TokenDetailsDTO tokenDetailsDTO = authenticationService.refreshToken(request, response);
        Map<String, Object> map = new HashMap<>();
        map.put("access_token", tokenDetailsDTO.getAccessToken());
        map.put("expiredAt", tokenDetailsDTO.getAccessTokenExpiresAt());
        map.put("message", tokenDetailsDTO.getMessage());
        return ResponseEntity.ok().body(map);
    }
}
