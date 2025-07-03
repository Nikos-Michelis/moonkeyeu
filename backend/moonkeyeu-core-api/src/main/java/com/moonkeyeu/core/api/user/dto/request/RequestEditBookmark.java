package com.moonkeyeu.core.api.user.dto.request;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestEditBookmark {
    @NotBlank(message = "Current bookmark name is required.")
    @Size(max = 155, message = "Current bookmark name should contain no more than 155 characters.")
    private String currentName;
    @NotBlank(message = "New bookmark name is required.")
    @Size(max = 155, message = "New bookmark name should contain no more than 155 characters.")
    private String newName;
}
