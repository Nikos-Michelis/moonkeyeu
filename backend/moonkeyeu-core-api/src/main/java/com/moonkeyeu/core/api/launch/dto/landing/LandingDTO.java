package com.moonkeyeu.core.api.launch.dto.landing;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.moonkeyeu.core.api.launch.dto.DTOEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder(
        {
                "id",
                "attempt",
                "success",
                "description",
                "downrange_distance",
                "landing_zone",
                "landing_type",
        })
public class LandingDTO implements DTOEntity {
    @JsonProperty("id")
    private Long landingId;
    @JsonProperty("attempt")
    private Boolean attempt;
    @JsonProperty("success")
    private Boolean success;
    @JsonProperty("description")
    private String description;
    @JsonProperty("downrange_distance")
    private Integer downrangeDistance;
    @JsonProperty("landing_zone")
    private LandingZoneDTO landingZone;
    @JsonProperty("landing_type")
    private LandingTypeDTO landingType;
}
