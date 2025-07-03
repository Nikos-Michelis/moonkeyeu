package com.moonkeyeu.core.api.security.services.otp;

import com.moonkeyeu.core.api.security.dto.request.OtpResendRequest;
import com.moonkeyeu.core.api.security.model.otp.OtpResend;
import com.moonkeyeu.core.api.security.model.otp.OtpToken;
import com.moonkeyeu.core.api.settings.exceptions.OtpLimitException;
import com.moonkeyeu.core.api.user.model.User;
import jakarta.mail.MessagingException;

public interface OtpResendService {
    OtpToken resendOtp(OtpResendRequest otpResendRequest) throws MessagingException, OtpLimitException;
    User isValidUser(User user, OtpResend otpResend);
}
