package com.moonkeyeu.etl.api.dto.clean.pads;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.moonkeyeu.etl.api.dto.clean.CsvEntity;
import com.moonkeyeu.etl.api.dto.clean.ImageEntity;
import lombok.Data;


@Data
@JsonPropertyOrder({
        "launch_pad_id",
        "active",
        "name",
        "description",
        "info_url",
        "wiki_url",
        "map_url",
        "latitude",
        "longitude",
        "map_image",
        "total_launch_count",
        "orbital_launch_attempt_count",
        "agency_id",
        "location_id"
})
@JsonIgnoreProperties(ignoreUnknown = true)
public class LaunchPad implements CsvEntity, ImageEntity {
    @JsonProperty("launch_pad_id")
    private String launch_pad_id;
    @JsonProperty("active")
    private Boolean active;
    @JsonProperty("launch_pad_name")
    private String name;
    @JsonProperty("launch_pad_description")
    private String description;
    @JsonProperty("info_url")
    private String info_url;
    @JsonProperty("wiki_url")
    private String wiki_url;
    @JsonProperty("map_url")
    private String map_url;
    @JsonProperty("latitude")
    private String latitude;
    @JsonProperty("longitude")
    private String longitude;
    @JsonProperty("map_image")
    private String map_image;
    @JsonProperty("total_pad_launches")
    private String total_launch_count;
    @JsonProperty("total_orbital_launch_attempts")
    private String orbital_launch_attempt_count;
    @JsonProperty("agency_id")
    private Integer agency_id;
    @JsonProperty("location_id")
    private Integer location_id;

    @Override
    public String getPrimaryKey() {
        return launch_pad_id;
    }

    @Override
    public String getImageUrl() {
        return map_image;
    }

    @Override
    public void setImageUrl(String imageUrl) {
        this.map_image = imageUrl;
    }
}