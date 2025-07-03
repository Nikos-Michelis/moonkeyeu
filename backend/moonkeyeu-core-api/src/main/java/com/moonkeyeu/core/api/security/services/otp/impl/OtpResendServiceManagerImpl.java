package com.moonkeyeu.core.api.security.services.otp.impl;

import com.moonkeyeu.core.api.security.model.otp.OtpResend;
import com.moonkeyeu.core.api.security.model.otp.OtpToken;
import com.moonkeyeu.core.api.security.services.otp.OtpResendServiceManager;
import com.moonkeyeu.core.api.security.repository.OtpResendRepository;
import com.moonkeyeu.core.api.user.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
@Slf4j
public class OtpResendServiceManagerImpl implements OtpResendServiceManager {

    private final OtpResendRepository otpResendRepository;

    public void saveOtpResend(OtpResend otpResend, OtpToken otpToken, User user) {
        otpResend.setUser(user);
        otpResend.setOtpType(otpToken.getOtpType().name());
        otpResend.setOtpResendCount(otpResend.getOtpResendCount() + 1);
        otpResend.setLastOtpSentTime(Instant.now());
        log.info("Recorded OTP resend for user " + otpResend.getUser().getUserId());
        otpResendRepository.save(otpResend);
    }
    public void resetOtpResendCount(OtpResend otpResend) {
        otpResend.setOtpResendCount(0);
        log.info("OtpResend --> " + otpResend);
        otpResendRepository.save(otpResend);
        log.info("Reset OTP resend count for user " + otpResend.getUser().getUserId());
    }

}
