package com.moonkeyeu.core.api.security.services.otp;

import com.moonkeyeu.core.api.security.model.otp.OtpResend;
import com.moonkeyeu.core.api.security.model.otp.OtpToken;
import com.moonkeyeu.core.api.user.model.User;

public interface OtpResendServiceManager {
    void saveOtpResend(OtpResend otpResend, OtpToken otpToken, User user);
    void resetOtpResendCount(OtpResend otpResend);
}
