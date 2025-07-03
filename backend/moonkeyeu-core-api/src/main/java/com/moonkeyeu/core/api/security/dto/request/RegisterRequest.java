package com.moonkeyeu.core.api.security.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.moonkeyeu.core.api.security.validators.ExistsByEmail;
import com.moonkeyeu.core.api.security.validators.ExistsByUsername;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RegisterRequest {
    @NotBlank(message = "Fullname must not be empty")
    @Pattern(regexp = "[A-Za-z][A-Za-z0-9_]{7,16}",
            message = "Username must be between 7 and 16 characters long and cannot contain spaces.")
    @ExistsByUsername(message = "Username is already taken.")
    private String username;
    @NotBlank(message = "Email must not be empty")
    @Email(message = "Email is not properly formatted")
    @ExistsByEmail(message = "Email is already registered.")
    private String email;
    @NotBlank(message = "Password must not be empty")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "Password should be at least 8 characters and include, at least 1 UPPERCASE letter, 1 number, 1 special character!")
    private String password;
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "Password should be at least 8 characters and include, at least 1 UPPERCASE letter, 1 number, 1 special character!")
    @JsonProperty("repeatPassword")
    private String repeatPassword;
}