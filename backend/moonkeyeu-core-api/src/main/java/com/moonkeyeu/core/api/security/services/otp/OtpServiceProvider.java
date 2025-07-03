package com.moonkeyeu.core.api.security.services.otp;

import com.moonkeyeu.core.api.security.model.otp.OtpToken;
import com.moonkeyeu.core.api.security.email.EmailDetails;
import com.moonkeyeu.core.api.security.model.otp.OtpType;
import com.moonkeyeu.core.api.user.model.User;
import jakarta.mail.MessagingException;

public interface OtpServiceProvider {
    void buildEmail(User user, OtpToken otpToken, EmailDetails emailDetails) throws MessagingException;
    OtpToken issueOtpCode(User user, OtpType otpType);
}
