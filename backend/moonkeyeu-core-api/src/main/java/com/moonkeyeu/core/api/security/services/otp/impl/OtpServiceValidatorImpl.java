package com.moonkeyeu.core.api.security.services.otp.impl;

import com.moonkeyeu.core.api.security.dto.request.OtpValidationRequest;
import com.moonkeyeu.core.api.security.model.otp.OtpToken;
import com.moonkeyeu.core.api.security.services.lock.UserStatusService;
import com.moonkeyeu.core.api.security.services.otp.OtpResendServiceManager;
import com.moonkeyeu.core.api.security.services.otp.OtpServiceValidator;
import com.moonkeyeu.core.api.security.repository.OtpResendRepository;
import com.moonkeyeu.core.api.settings.exceptions.InvalidOtpException;
import com.moonkeyeu.core.api.settings.exceptions.OtpLimitException;
import com.moonkeyeu.core.api.user.model.User;
import com.moonkeyeu.core.api.user.reporitory.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.LockedException;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class OtpServiceValidatorImpl implements OtpServiceValidator {

    @Value("${application.otp.max-validation-attempts}")
    private int MAX_ATTEMPTS;
    private final UserRepository userRepository;
    private final UserStatusService userStatusService;
    private final OtpResendRepository otpResendRepository;
    private final OtpResendServiceManager otpResendServiceManager;

    public void isValidOtp(OtpToken otpToken, OtpValidationRequest otpValidationRequest, User user) {
        if (userStatusService.isAccountLocked(user)) {
            throw new LockedException("Your account is temporary locked until "
                    + userStatusService.getLockExpiration(user) + ".");
        }

        if (otpToken.getOtp().equals(otpValidationRequest.getOtp())) {
            if(Instant.now().isBefore(otpToken.getExpiresAt())) {
                otpResendRepository.findByUserIdAndOtpType(otpToken.getUser().getUserId(), otpToken.getOtpType().name())
                        .ifPresent(otpResendServiceManager::resetOtpResendCount);
                userStatusService.unlockAccount(user);
            }else {
                throw new InvalidOtpException("Expired OTP try to resend.");
            }
        } else {
           handleInvalidOtp(user);
        }
    }
    public void handleInvalidOtp(User user) {
        user.setAttempts(user.getAttempts() != null? user.getAttempts() + 1 : 1);
        userRepository.save(user);
        if (user.getAttempts() >= MAX_ATTEMPTS) {
            userStatusService.lockAccount(user, false);
            throw new OtpLimitException("Invalid OTP. Your account has been locked due to multiple failed attempts. Try again in " + user.getLockExpiresAt());
        } else {
            throw new InvalidOtpException("Invalid OTP. You have " + user.getAttempts() + "/" + MAX_ATTEMPTS + " attempts.");
        }
    }
}
