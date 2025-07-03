package com.moonkeyeu.etl.api.dto.clean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@Data
@JsonPropertyOrder({"location_id", "name", "country_code", "description", "map_image", "location_timezone", "total_launch_count", "total_landing_count"})
@JsonIgnoreProperties(ignoreUnknown = true)
public class Location implements CsvEntity {
    @JsonProperty("location_id")
    private String location_id;
    @JsonProperty("location_name")
    private String name;
    @JsonProperty("location_description")
    private String description;
    @JsonProperty("location_map_image")
    private String map_image;
    @JsonProperty("location_timezone")
    private String location_timezone;
    @JsonProperty("total_location_launches")
    private String total_launch_count;
    @JsonProperty("total_location_landings")
    private String total_landing_count;

    @Override
    public String getPrimaryKey() {
        return location_id;
    }
}
