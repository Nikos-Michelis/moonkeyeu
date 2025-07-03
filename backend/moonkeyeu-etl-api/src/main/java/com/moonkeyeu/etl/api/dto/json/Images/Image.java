package com.moonkeyeu.etl.api.dto.json.Images;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Image {
    @JsonProperty("id")
    private Integer image_id;
    @JsonProperty("name")
    private String image_name;
    private String image_url;
    private String thumbnail_url;
    private String credit;
    @JsonProperty("agency_id")
    private Integer agency_id;
    @JsonProperty("manufacturer_id")
    private Integer manufacturer_id;
    @JsonProperty("configuration_id")
    private Integer configuration_id;
    @JsonProperty("launch_id")
    private String launch_id;
    @JsonProperty("spacecraft_id")
    private Integer spacecraft_id;
    @JsonProperty("launcher_id")
    private Integer launcher_id;
    @JsonProperty("astronaut_id")
    private Integer astronaut_id;
    @JsonProperty("launch_pad_id")
    private Integer launch_pad_id;

}
