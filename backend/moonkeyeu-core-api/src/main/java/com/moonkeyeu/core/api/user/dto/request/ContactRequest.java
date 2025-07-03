package com.moonkeyeu.core.api.user.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Data
public class ContactRequest {
    @NotBlank(message = "Email is required")
    @Email(message = "Please provide a valid email address")
    private String email;
    @NotNull(message = "Category is required")
    private String category;
    @NotBlank(message = "Message is required")
    @Size(min = 10, max = 350, message = "Message should be between 10 and 350 characters long")
    private String message;
}
