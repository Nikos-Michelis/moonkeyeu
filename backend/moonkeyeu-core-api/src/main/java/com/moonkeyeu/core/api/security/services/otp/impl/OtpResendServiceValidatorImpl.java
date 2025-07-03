package com.moonkeyeu.core.api.security.services.otp.impl;

import com.moonkeyeu.core.api.security.model.otp.OtpResend;
import com.moonkeyeu.core.api.security.services.lock.UserStatusService;
import com.moonkeyeu.core.api.security.services.otp.OtpResendServiceValidator;
import com.moonkeyeu.core.api.settings.exceptions.OtpLimitException;
import com.moonkeyeu.core.api.user.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class OtpResendServiceValidatorImpl implements OtpResendServiceValidator {
    @Value("${application.otp.max-resend-attempts}")
    private int MAX_RESEND_ATTEMPTS;
    @Value("${application.otp.cooldown-seconds}")
    private long RESEND_COOLDOWN_SECONDS = 60;
    private final UserStatusService userStatusService;

    public void validateResendAttempts(OtpResend otpResend, User user) {
        if (isExceedMaxAttempts(otpResend, user)) {
            log.warn("User " + otpResend.getUser().getUserId() + " has exceeded the maximum resend attempts.");
            throw new OtpLimitException("Exceeded maximum resend attempts.");
        }

        if (isUnderCooldown(otpResend)) {
            log.warn("User " + otpResend.getUser().getUserId() + " is within the cooldown period for resending OTP.");
            throw new OtpLimitException("Wait until exceeded the cooldown period " + RESEND_COOLDOWN_SECONDS + " seconds.", RESEND_COOLDOWN_SECONDS * 1000);
        }
    }

    public boolean isExceedMaxAttempts(OtpResend otpResend, User user) {
        if (otpResend.getOtpResendCount() >= MAX_RESEND_ATTEMPTS) {
            userStatusService.lockAccount(user, false);
            return true;
        }
        return false;
    }

    public boolean isUnderCooldown(OtpResend otpResend) {
        if (otpResend.getLastOtpSentTime() != null) {
            long secondsSinceLastOtp = ChronoUnit.SECONDS.between(otpResend.getLastOtpSentTime(), Instant.now());
            if (secondsSinceLastOtp < RESEND_COOLDOWN_SECONDS) {
                log.warn("User " + otpResend.getUser().getUserId() + " is within the cooldown period for resending OTP.");
                return true;
            }
        }
        return false;
    }
}
