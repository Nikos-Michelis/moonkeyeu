package com.moonkeyeu.core.api.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.moonkeyeu.core.api.launch.dto.DTOEntity;
import com.moonkeyeu.core.api.user.model.Category;
import lombok.*;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContactDTO implements DTOEntity {
    @JsonProperty("id")
    private Long contactId;
    @JsonProperty("email")
    private String email;
    @JsonProperty("category")
    private Category category;
    @JsonProperty("message")
    private String message;
    @JsonProperty("created_at")
    private Instant createdAt;
    @JsonProperty("updated_at")
    private Instant updatedAt;
}
