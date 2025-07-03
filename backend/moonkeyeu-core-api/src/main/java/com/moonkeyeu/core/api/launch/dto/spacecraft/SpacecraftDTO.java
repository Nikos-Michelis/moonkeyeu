package com.moonkeyeu.core.api.launch.dto.spacecraft;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.moonkeyeu.core.api.launch.dto.DTOEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder(
        {
                "id",
                "name",
                "serial_number",
                "in_space",
                "flights_count",
                "mission_ends_count",
                "description",
                "status",
                "configuration"
        })
public class SpacecraftDTO implements DTOEntity {
    @JsonProperty("id")
    private Long spacecraftId;
    @JsonProperty("name")
    private String name;
    @JsonProperty("serial_number")
    private String serialNumber;
    @JsonProperty("in_space")
    private Boolean inSpace;
    @JsonProperty("flights_count")
    private Integer flightsCount;
    @JsonProperty("mission_ends_count")
    private Integer missionEndsCount;
    @JsonProperty("description")
    private String description;
    @JsonProperty("status")
    private String statusName;
    @JsonProperty("configuration")
    private SpacecraftConfigurationDTO spacecraftConfiguration;

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        SpacecraftDTO that = (SpacecraftDTO) object;
        return Objects.equals(spacecraftId, that.spacecraftId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(spacecraftId);
    }
}
