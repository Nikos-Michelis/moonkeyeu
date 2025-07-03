package com.moonkeyeu.etl.api.dto.json.media;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SocialMedia {
    @JsonProperty("id")
    private int social_id;
    private String media_name;
    @JsonProperty("url")
    private String media_url;
    private Integer astronaut_id;
}
