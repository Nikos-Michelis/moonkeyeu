package com.moonkeyeu.core.api.security.services.otp.impl;

import com.moonkeyeu.core.api.security.model.otp.OtpToken;
import com.moonkeyeu.core.api.security.model.otp.OtpType;
import com.moonkeyeu.core.api.security.services.otp.OtpServiceBuilder;
import com.moonkeyeu.core.api.security.util.RequestTokenBuilder;
import com.moonkeyeu.core.api.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.Instant;

@Service
@RequiredArgsConstructor
public class OtpServiceBuilderImpl implements OtpServiceBuilder {

    private final RequestTokenBuilder requestTokenBuilder;
    @Value("${application.otp.expiration}")
    private long OTP_EXPIRATION; // 300 second = 5min
    @Value("${application.otp.length}")
    private int OTP_LENGTH;

    @Override
    public OtpToken generateOtpCode(User user, OtpType otpType) {
        String generatedToken = generateActivationCode(OTP_LENGTH);
        return OtpToken.builder()
                .token(requestTokenBuilder.generateToken())
                .otp(generatedToken)
                .otpType(otpType)
                .isRedeemed(false)
                .createdAt(Instant.now())
                .expiresAt(Instant.now().plusSeconds(OTP_EXPIRATION))
                .user(user)
                .build();
    }
    @Override
    public String generateActivationCode(int length) {
        String characters = "0123456789";
        StringBuilder codeBuilder = new StringBuilder();
        SecureRandom secureRandom = new SecureRandom();
        for (int i = 0; i < length; i++) {
            int randomIndex = secureRandom.nextInt(characters.length());
            codeBuilder.append(characters.charAt(randomIndex));
        }
        return codeBuilder.toString();
    }

}