package com.moonkeyeu.core.api.security.services.otp;

import com.moonkeyeu.core.api.security.dto.request.OtpValidationRequest;
import com.moonkeyeu.core.api.security.model.otp.OtpToken;
import com.moonkeyeu.core.api.user.model.User;

public interface OtpServiceValidator {
    void isValidOtp(OtpToken otpToken, OtpValidationRequest otpValidationRequest, User user);
    void handleInvalidOtp(User user);
}
