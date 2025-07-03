package com.moonkeyeu.core.api.launch.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder(
        {
                "id",
                "name",
                "description",
                "map_image",
                "location_timezone",
                "total_launch_count",
                "total_landing_count",
        })
public class LocationDTO implements DTOEntity {
    @JsonProperty("id")
    private Long locationId;
    @JsonProperty("name")
    private String name;
    @JsonProperty("description")
    private String description;
    @JsonProperty("map_image")
    private String mapImage;
    @JsonProperty("location_timezone")
    private String locationTimezone;
    @JsonProperty("total_launch_count")
    private Integer totalLaunchCount;
    @JsonProperty("total_landing_count")
    private Integer totalLandingCount;

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        LocationDTO that = (LocationDTO) object;
        return Objects.equals(locationId, that.locationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(locationId);
    }
}
