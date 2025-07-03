package com.moonkeyeu.core.api.launch.dto.landing;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.moonkeyeu.core.api.launch.dto.DTOEntity;
import com.moonkeyeu.core.api.launch.dto.LocationDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({"landing_zone_id", "name", "abbrev", "description", "successful_landings", "location"})
public class LandingZoneDTO implements DTOEntity {
    @JsonProperty("id")
    private Long landingZoneId;
    @JsonProperty("name")
    private String name;
    @JsonProperty("abbrev")
    private String abbrev;
    @JsonProperty("description")
    private String description;
    @JsonProperty("successful_landings")
    private Integer successfulLandings;
    @JsonProperty("location")
    private LocationDTO location;
}
