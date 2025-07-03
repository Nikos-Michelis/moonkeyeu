package com.moonkeyeu.core.api.security.services;

import com.moonkeyeu.core.api.security.model.SignUpProvider;
import com.moonkeyeu.core.api.security.services.lock.UserStatusService;
import com.moonkeyeu.core.api.user.model.User;
import com.moonkeyeu.core.api.user.services.impl.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AuthenticationManagerImpl implements AuthenticationManager {

    @Value("${application.otp.max-validation-attempts}")
    private int MAX_ATTEMPTS;
    private final AuthenticationProvider authenticationProvider;
    private final UserDetailsServiceImpl userDetailsService;
    private final UserStatusService userStatusService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        try {
            Authentication auth = authenticationProvider.authenticate(authentication);
            User user = (User) auth.getPrincipal();
            if (!userStatusService.isAccountLocked(user)) {
                userStatusService.unlockAccount(user);
            }
            return auth;
        } catch (BadCredentialsException e) {
            User user = (User) userDetailsService.loadUserByUsername(authentication.getName());

            boolean hasSignUpMethod = user.getSignUpMethods().stream()
                    .anyMatch(method -> method.getProvider().equalsIgnoreCase(SignUpProvider.Password.name()));

            if (hasSignUpMethod) {
                userStatusService.attemptsIncrement(user);
            }

            if (user.getAttempts() != null && user.getAttempts() >= MAX_ATTEMPTS) {
                userStatusService.lockAccount(user, true);
                throw new BadCredentialsException(
                        "Invalid Credentials. Your account has been locked due to multiple failed attempts. Try again after "
                                + userStatusService.getLockExpiration(user));
            }
            throw new BadCredentialsException("Invalid Credentials");
        } catch (LockedException e){
            User user = (User) userDetailsService.loadUserByUsername(authentication.getName());
            if (!userStatusService.isAccountLocked(user)) {
                userStatusService.resetAccount(user);
            }
            throw new LockedException("Your account is temporarily locked for "
                    + userStatusService.getLockExpiration(user) + ". Please try again later.");
        }
    }
}
