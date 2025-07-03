package com.moonkeyeu.etl.api.dto.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class LaunchPad implements Serializable {
    @JsonProperty("id")
    private String launch_pad_id;
    @JsonProperty("active")
    private Boolean active;
    @JsonProperty("name")
    private String launch_pad_name;
    @JsonProperty("description")
    private String launch_pad_description;
    @JsonProperty("info_url")
    private String info_url;
    @JsonProperty("wiki_url")
    private String wiki_url;
    @JsonProperty("map_url")
    private String map_url;
    @JsonProperty("latitude")
    private Double latitude;
    @JsonProperty("longitude")
    private Double longitude;
    @JsonProperty("location")
    private Location location;
    @JsonProperty("country")
    private PadCountry country;
    @JsonProperty("image")
    private PadImages image;
    @JsonProperty("map_image")
    private String map_image;
    @JsonProperty("total_launch_count")
    private Integer total_pad_launches;
    @JsonProperty("orbital_launch_attempt_count")
    private Integer total_orbital_launch_attempts;
}
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
class PadCountry {
    @JsonProperty("id")
    private int pad_country_id;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
class PadImages {
    @JsonProperty("id")
    private Integer pad_image_id;
    @JsonProperty("name")
    private String pad_image_name;
    @JsonProperty("image_url")
    private String pad_image_url;
    @JsonProperty("thumbnail_url")
    private String pad_thumbnail_url;
    @JsonProperty("credit")
    private String pad_credit;
}

