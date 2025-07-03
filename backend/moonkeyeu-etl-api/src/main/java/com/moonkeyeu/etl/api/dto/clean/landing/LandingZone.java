package com.moonkeyeu.etl.api.dto.clean.landing;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.moonkeyeu.etl.api.dto.clean.CsvEntity;
import lombok.Data;

@Data
@JsonPropertyOrder({"landing_zone_id", "name", "country_code", "description", "map_image", "location_timezone", "total_launch_count", "total_landing_count"})
@JsonIgnoreProperties(ignoreUnknown = true)
public class LandingZone implements CsvEntity {
    @JsonProperty("landing_zone_id")
    private String landing_zone_id;
    @JsonProperty("landing_location_name")
    private String name;
    @JsonProperty("landing_location_abbrev")
    private String abbrev;
    @JsonProperty("landing_location_description")
    private String description;
    @JsonProperty("zone_successful_landings")
    private Integer successful_landings;
    @JsonProperty("location_id")
    private Integer location_id;

    @Override
    public String getPrimaryKey() {
        return landing_zone_id;
    }
}

