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
public class OAuth2Request {
    @NotBlank(message = "Fullname must not be empty")
    @Pattern(regexp = "[A-Za-z][A-Za-z0-9_]{7,16}",
            message = "Username must be between 7 and 16 characters long and cannot contain spaces.")
    @ExistsByUsername(message = "Username is already taken.")
    @JsonProperty("confirmUsername")
    private String username;
    @Email(message = "Invalid email format")
    @NotEmpty(message = "Email is mandatory")
    @NotNull(message = "Email is mandatory")
    @ExistsByEmail(message = "Email is already registered.")
    private String email;
    @NotBlank(message = "Token must not be blank")
    private String idToken;
}
