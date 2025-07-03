package com.moonkeyeu.core.api.security.services.otp;

import com.moonkeyeu.core.api.security.model.otp.OtpResend;
import com.moonkeyeu.core.api.user.model.User;

public interface OtpResendServiceValidator {
    void validateResendAttempts(OtpResend otpResend, User user);
    boolean isExceedMaxAttempts(OtpResend otpResend, User user);
    boolean isUnderCooldown(OtpResend otpResend);
}
