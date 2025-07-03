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
public class InfoUrl {
    private String launch_id;
    private Integer priority;
    private String source;
    private String title;
    @JsonProperty("description")
    private String description;
    private String feature_image;
    @JsonProperty("url")
    private String url;
}
