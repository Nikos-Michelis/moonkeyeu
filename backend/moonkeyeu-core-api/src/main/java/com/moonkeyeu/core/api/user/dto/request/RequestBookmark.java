package com.moonkeyeu.core.api.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestBookmark {
    @NotBlank(message = "Bookmark name is required.")
    @Size(max = 155, message = "Bookmark name should contain no more than 155 characters.")
    private String bookmarkName;
    @NotBlank(message = "Launch id is required.")
    private String launchId;
}
