package com.moonkeyeu.etl.api.dto.json.media;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MissionPatches {
    private String launch_id;
    @JsonProperty("id")
    private Integer patch_id;
    @JsonProperty("priority")
    private Integer priority;
    @JsonProperty("name")
    private String patch_name;
    @JsonProperty("image_url")
    private String patch_image_url;
}
