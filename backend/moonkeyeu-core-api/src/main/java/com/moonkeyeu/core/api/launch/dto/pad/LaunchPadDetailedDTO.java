package com.moonkeyeu.core.api.launch.dto.pad;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.moonkeyeu.core.api.launch.dto.DTOEntity;
import com.moonkeyeu.core.api.launch.dto.LocationDTO;
import com.moonkeyeu.core.api.launch.dto.agency.AgencyNormalDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Objects;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder(
        {
                "id",
                "name",
                "active",
                "description",
                "info_url",
                "wiki_url",
                "map_image",
                "total_launch_count",
                "location",
                "agencies",
        })
public class LaunchPadDetailedDTO implements DTOEntity {
    @JsonProperty("id")
    private Long launchPadId;
    @JsonProperty("name")
    private String launchPadName;
    @JsonProperty("active")
    private Boolean active;
    @JsonProperty("description")
    private String description;
    @JsonProperty("info_url")
    private String infoUrl;
    @JsonProperty("wiki_url")
    private String wikiUrl;
    @JsonProperty("map_image")
    private String mapImage;
    @JsonProperty("total_launch_count")
    private Integer totalLaunchCount;
    @JsonProperty("location")
    private LocationDTO location;
    @JsonProperty("agencies")
    private Set<AgencyNormalDTO> agencies;

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        LaunchPadDetailedDTO that = (LaunchPadDetailedDTO) object;
        return Objects.equals(launchPadId, that.launchPadId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(launchPadId);
    }
}
