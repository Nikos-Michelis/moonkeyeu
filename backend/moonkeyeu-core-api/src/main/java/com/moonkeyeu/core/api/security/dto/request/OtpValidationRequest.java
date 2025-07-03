package com.moonkeyeu.core.api.security.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OtpValidationRequest {

    @NotBlank(message = "OTP must not be blank")
    @Size(min = 6, max = 6, message = "OTP must be exactly 6 digits")
    private String otp;
    @NotBlank(message = "Token must not be blank")
    private String token;
    @NotNull(message = "RememberMe flag must not be null")
    private Boolean rememberMe;
}
