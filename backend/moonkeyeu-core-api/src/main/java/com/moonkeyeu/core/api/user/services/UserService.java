package com.moonkeyeu.core.api.user.services;

import com.moonkeyeu.core.api.security.dto.request.ChangePasswordRequest;
import com.moonkeyeu.core.api.security.dto.request.ResetCredentialsRequest;
import com.moonkeyeu.core.api.security.dto.request.ResetPasswordRequest;
import com.moonkeyeu.core.api.user.dto.UserDTO;
import com.moonkeyeu.core.api.user.model.User;
import jakarta.mail.MessagingException;

import java.util.List;

public interface UserService {
    void generatePasswordResetToken(ResetCredentialsRequest resetCredentialsRequest) throws MessagingException;

    void resetPassword(ResetPasswordRequest resetPasswordRequest);

    void changePassword(ChangePasswordRequest changePasswordRequest, User user);
    UserDTO getAuthUserDetails(User user);
}
