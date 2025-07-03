package com.moonkeyeu.core.api.security.services;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.moonkeyeu.core.api.security.dto.OAuth2GoogleDTO;
import com.moonkeyeu.core.api.security.dto.TokenDTO;
import com.moonkeyeu.core.api.security.dto.request.AuthenticationRequest;
import com.moonkeyeu.core.api.security.dto.request.OAuth2Request;
import com.moonkeyeu.core.api.security.dto.request.OtpValidationRequest;
import com.moonkeyeu.core.api.security.dto.request.RegisterRequest;
import com.moonkeyeu.core.api.security.dto.response.TokenDetailsDTO;
import com.moonkeyeu.core.api.security.email.EmailDetails;
import com.moonkeyeu.core.api.security.email.EmailTemplateName;
import com.moonkeyeu.core.api.security.model.SignUpProvider;
import com.moonkeyeu.core.api.security.model.otp.OtpToken;
import com.moonkeyeu.core.api.security.model.otp.OtpType;
import com.moonkeyeu.core.api.security.model.token.jwt.Token;
import com.moonkeyeu.core.api.security.model.token.jwt.TokenScope;
import com.moonkeyeu.core.api.security.repository.ProviderRepository;
import com.moonkeyeu.core.api.security.services.cookie.CookieServiceProvider;
import com.moonkeyeu.core.api.security.services.jwt.JwtRequestExtractor;
import com.moonkeyeu.core.api.security.services.jwt.JwtServiceParserImpl;
import com.moonkeyeu.core.api.security.services.jwt.JwtServiceProvider;
import com.moonkeyeu.core.api.security.services.otp.OtpServiceProvider;
import com.moonkeyeu.core.api.security.services.otp.impl.OtpServiceValidatorImpl;
import com.moonkeyeu.core.api.settings.exceptions.*;
import com.moonkeyeu.core.api.user.model.User;
import com.moonkeyeu.core.api.user.reporitory.UserRepository;
import com.moonkeyeu.core.api.security.repository.TokenRepository;
import com.moonkeyeu.core.api.security.repository.RoleRepository;
import com.moonkeyeu.core.api.security.repository.OtpRepository;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ProviderRepository providerRepository;
    private final OtpRepository otpRepository;
    private final TokenRepository tokenRepository;
    private final OtpServiceProvider otpServiceProvider;
    private final OtpServiceValidatorImpl otpServiceValidatorImpl;
    private final PasswordEncoder passwordEncoder;
    private final JwtServiceProvider jwtServiceProvider;
    private final JwtRequestExtractor jwtRequestExtractor;
    private final JwtServiceParserImpl jwtServiceParserImpl;
    private final AuthenticationManagerImpl authenticationManagerImpl;
    private final CookieServiceProvider cookieServiceProvider;
    private final GoogleTokenVerifierService googleTokenVerifierService;

    @Transactional
    public OtpToken register(RegisterRequest request) throws MessagingException {
        if (!request.getPassword().equals(request.getRepeatPassword())) {
            throw new BadCredentialsException("Password do not match.");
        }

        var userRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new IllegalStateException("Role was not initialized."));

        var signUpMethod = providerRepository.findByProvider(SignUpProvider.Password.name())
                .orElseThrow(() -> new IllegalStateException("SignUp provider was not initialized."));


        User newUser = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .accountLocked(false)
                .signUpMethods(Set.of(signUpMethod))
                .enable(true)
                .roles(Set.of(userRole))
                .build();

        userRepository.save(newUser);
        OtpToken otpToken = otpServiceProvider.issueOtpCode(newUser, OtpType.REGISTER);
        otpServiceProvider.buildEmail(
                newUser,
                otpToken,
                EmailDetails.builder()
                        .subject("Account Verification")
                        .emailTemplateName(EmailTemplateName.VERIFY_ACCOUNT)
                        .build());
        return otpToken;
    }
   @Transactional(noRollbackFor = {BadCredentialsException.class, LockedException.class})
   public OtpToken authenticate(AuthenticationRequest request) throws MessagingException {

        var auth = authenticationManagerImpl.authenticate(
               new UsernamePasswordAuthenticationToken(
                       request.getEmail(),
                       request.getPassword()
               )
       );

       var user = ((User) auth.getPrincipal());
       OtpToken otpToken = otpServiceProvider.issueOtpCode(user, OtpType.LOGIN);
       otpServiceProvider
               .buildEmail(
                       user,
                       otpToken,
                       EmailDetails.builder()
                               .subject("Account Verification")
                               .emailTemplateName(EmailTemplateName.VERIFY_ACCOUNT)
                               .build()
               );
       return otpToken;
   }
   @Transactional(noRollbackFor = {OtpLimitException.class, InvalidOtpException.class})
   public TokenDetailsDTO VerifyOtp(OtpValidationRequest otpValidationRequest) {
       OtpToken otpToken = otpRepository.findByToken(otpValidationRequest.getToken())
               .filter(otp -> (otp.getValidatedAt() == null
                       && !otp.isRedeemed())
                       && (!otp.isRevoked()
                       && !otp.isExpired()))
               .orElseThrow(() -> new InvalidOtpException("Invalid otp token."));

       User user = userRepository.findByEmail(otpToken.getUser().getEmail())
               .filter(User::isEnable)
               .orElseThrow(() -> new UsernameNotFoundException("User not found."));
       otpServiceValidatorImpl.isValidOtp(otpToken, otpValidationRequest, user);
       if (user.getValidatedAt() == null) {
           user.setValidatedAt(Instant.now());
       }
       otpToken.setValidatedAt(Instant.now());
       revokeOtpToken(otpToken);
       revokeAllUserTokens(user);
       Token accessToken = jwtServiceProvider.generateAccessToken(user);
       Token refreshToken = jwtServiceProvider.generateRefreshToken(user, otpValidationRequest.getRememberMe());
       tokenRepository.save(refreshToken);
       return TokenDetailsDTO.builder()
               .accessToken(accessToken.getToken())
               .refreshToken(refreshToken.getToken())
               .accessTokenIssuedAt(accessToken.getIssuedAt())
               .accessTokenExpiresAt(accessToken.getExpiresAt())
               .refreshTokenIssuedAt(refreshToken.getIssuedAt())
               .refreshTokenExpiresAt(refreshToken.getExpiresAt())
               .message("Login Successful.")
               .build();
   }

    @Transactional
    public TokenDetailsDTO OAuth2GoogleRegister(OAuth2Request oAuth2Request){
      GoogleIdToken.Payload payload = googleTokenVerifierService.verify(oAuth2Request.getIdToken());

      if (payload == null) {
          throw new InvalidGoogleJwtToken("Invalid Google ID token");
      }

       var userRole = roleRepository.findByName("USER")
               .orElseThrow(() -> new IllegalStateException("Role was not initialized."));

       var signUpMethod = providerRepository.findByProvider(SignUpProvider.Google.name())
               .orElseThrow(() -> new IllegalStateException("SignUp provider was not initialized."));

       User newUser = User.builder()
               .username(oAuth2Request.getUsername())
               .email(oAuth2Request.getEmail())
               .accountLocked(false)
               .enable(true)
               .validatedAt(Instant.now())
               .signUpMethods(Set.of(signUpMethod))
               .roles(Set.of(userRole))
               .build();

       userRepository.save(newUser);
       revokeAllUserTokens(newUser);
       Token accessToken = jwtServiceProvider.generateAccessToken(newUser);
       Token refreshToken = jwtServiceProvider.generateRefreshToken(newUser, true);
       tokenRepository.save(refreshToken);
       return TokenDetailsDTO.builder()
               .accessToken(accessToken.getToken())
               .refreshToken(refreshToken.getToken())
               .accessTokenIssuedAt(accessToken.getIssuedAt())
               .accessTokenExpiresAt(accessToken.getExpiresAt())
               .refreshTokenIssuedAt(refreshToken.getIssuedAt())
               .refreshTokenExpiresAt(refreshToken.getExpiresAt())
               .message("Registration Successful.")
               .build();
    }

    @Transactional
    public OAuth2GoogleDTO OAuth2GoogleLogin(Map<String, String> authMap){
        GoogleIdToken.Payload payload = googleTokenVerifierService.verify(authMap.get("idToken"));

        if (payload == null) {
            throw new InvalidGoogleJwtToken("Invalid Google ID token");
        }

        String email = payload.getEmail();
        Optional<User> optionalUser = userRepository.findByEmail(email).filter(User::isEnable);

        if (optionalUser.isEmpty()) {
            return OAuth2GoogleDTO.builder().googleTokenPayload(payload).build();
        }

        User user = optionalUser.get();
        user.setAttempts(0);
        user.setBlocks(0);
        user.setAccountLocked(false);
        handleGoogleSignUp(user);
        revokeAllUserTokens(user);
        Token accessToken = jwtServiceProvider.generateAccessToken(user);
        Token refreshToken = jwtServiceProvider.generateRefreshToken(user, true);
        tokenRepository.save(refreshToken);
        return OAuth2GoogleDTO.builder()
                .tokenDetails(
                        TokenDetailsDTO.builder()
                                .accessToken(accessToken.getToken())
                                .refreshToken(refreshToken.getToken())
                                .accessTokenIssuedAt(accessToken.getIssuedAt())
                                .accessTokenExpiresAt(accessToken.getExpiresAt())
                                .refreshTokenIssuedAt(refreshToken.getIssuedAt())
                                .refreshTokenExpiresAt(refreshToken.getExpiresAt())
                                .message("Login Successful.")
                                .build()
                ).build();
    }

    private void handleGoogleSignUp(User user){
        boolean hasSignUpMethod = user.getSignUpMethods().stream()
                .anyMatch(method -> method.getProvider().equalsIgnoreCase(SignUpProvider.Google.name()));

        if (!hasSignUpMethod) {
           var signUpMethods = providerRepository.findByProvider(SignUpProvider.Google.name())
                    .orElseThrow(() -> new IllegalStateException("Google sign up provider was not initialized."));
           user.addSignUpProvider(signUpMethods);
        }
    }

   @Transactional
   public void deleteAccount(User user) {
       User storedUser = userRepository.findByEmail(user.getEmail())
               .orElseThrow(() -> new UsernameNotFoundException("User not found."));
       storedUser.removeAllRoles();
       storedUser.removeAllSignUpMethods();
       userRepository.delete(storedUser);
       SecurityContextHolder.clearContext();
   }

   private void revokeOtpToken(OtpToken otpToken){
       otpToken.setValidatedAt(Instant.now());
       otpToken.setRedeemed(true);
       otpToken.setRevoked(true);
       otpToken.setExpired(true);
       otpRepository.save(otpToken);
   }

   private void revokeAllUserTokens(User user) {
       var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getUserId());
       if (validUserTokens.isEmpty()) {
           return;
       }
       validUserTokens.forEach(token -> {
           token.setExpired(true);
           token.setRevoked(true);
       });
       tokenRepository.saveAll(validUserTokens);
   }

   public TokenDetailsDTO refreshToken(HttpServletRequest request, HttpServletResponse response){
       String refreshToken = jwtRequestExtractor.extractToken(request)
               .orElseThrow(() -> new SessionNotFoundException("Session may be expired or user not logged in."));
       TokenDTO tokenDTO = jwtServiceParserImpl.parseToken(refreshToken);
       if (!jwtServiceProvider.isRefreshToken(refreshToken,
               TokenScope.valueOf(tokenDTO.getTokenScope().name().toUpperCase()))){
           throw new InvalidJwtTokenException("Invalid (JWT) refresh token.");
       }
       User user = userRepository.findByEmail(tokenDTO.getUserName())
               .filter(User::isEnable)
               .orElseThrow(() -> new UsernameNotFoundException("User not found."));
       boolean hasValidRefreshToken =
               tokenRepository.findAllValidTokenByUser(user.getUserId())
                       .stream()
                       .filter(t1 -> !t1.isRevoked() && !t1.isExpired())
                       .anyMatch(t1 -> jwtServiceProvider.isValidToken(t1.getToken(), user));
       if (!hasValidRefreshToken) {
           revokeAllUserTokens(user);
           cookieServiceProvider.clearExpiredAuthCookie(response);
           throw new SessionExpiredException("Your session has expired.");
       }
       Token accessToken = jwtServiceProvider.generateAccessToken(user);
       return TokenDetailsDTO.builder()
               .accessToken(accessToken.getToken())
               .accessTokenIssuedAt(accessToken.getIssuedAt())
               .accessTokenExpiresAt(accessToken.getExpiresAt())
               .message("The token has refreshed successfully.")
               .build();
   }
}
