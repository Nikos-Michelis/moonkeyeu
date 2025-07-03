package com.moonkeyeu.core.api.launch.dto.info;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.moonkeyeu.core.api.launch.dto.DTOEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder(
        {
                "social_id",
                "name",
                "media_url",
        })
public class SocialMediaDTO implements DTOEntity {
    @JsonProperty("social_id")
    private Long socialId;
    @JsonProperty("name")
    private String name;
    @JsonProperty("media_url")
    private String mediaUrl;
}
