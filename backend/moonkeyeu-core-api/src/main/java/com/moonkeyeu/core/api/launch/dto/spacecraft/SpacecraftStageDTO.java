package com.moonkeyeu.core.api.launch.dto.spacecraft;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.moonkeyeu.core.api.launch.dto.landing.LandingDTO;
import com.moonkeyeu.core.api.launch.dto.DTOEntity;
import com.moonkeyeu.core.api.launch.dto.astronaut.CrewMemberDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({"id", "destination", "spacecraft", "landing", "crew"})
public class SpacecraftStageDTO implements DTOEntity {
    @JsonProperty("id")
    private Long spacecraftStageId;
    @JsonProperty("destination")
    private String destination;
    @JsonProperty("spacecraft")
    private SpacecraftDTO spacecraft;
    @JsonProperty("landing")
    private LandingDTO landing;
    @JsonProperty("crew")
    private Set<CrewMemberDTO> crewMembers;

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        SpacecraftStageDTO that = (SpacecraftStageDTO) object;
        return Objects.equals(spacecraftStageId, that.spacecraftStageId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(spacecraftStageId);
    }
}
