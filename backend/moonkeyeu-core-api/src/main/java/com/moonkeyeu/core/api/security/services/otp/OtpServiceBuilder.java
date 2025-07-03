package com.moonkeyeu.core.api.security.services.otp;

import com.moonkeyeu.core.api.security.model.otp.OtpToken;
import com.moonkeyeu.core.api.security.model.otp.OtpType;
import com.moonkeyeu.core.api.user.model.User;

public interface OtpServiceBuilder {
    OtpToken generateOtpCode(User user, OtpType otpType);
    String generateActivationCode(int length);
}
