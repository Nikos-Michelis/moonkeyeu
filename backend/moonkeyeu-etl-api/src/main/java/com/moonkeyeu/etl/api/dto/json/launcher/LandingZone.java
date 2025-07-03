package com.moonkeyeu.etl.api.dto.json.launcher;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.moonkeyeu.etl.api.dto.json.Location;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class LandingZone {
    @JsonProperty("id")
    private Integer landing_zone_id;
    @JsonProperty("name")
    private String landing_location_name;
    @JsonProperty("abbrev")
    private String landing_location_abbrev;
    @JsonProperty("description")
    private String landing_location_description;
    @JsonProperty("location")
    private Location location;
    @JsonProperty("successful_landings")
    private Integer zone_successful_landings;
    @JsonProperty("attempted_landings")
    private Integer zone_attempted_landings;


}
