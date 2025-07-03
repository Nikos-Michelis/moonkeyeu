package com.moonkeyeu.core.api.launch.dto.pad;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.moonkeyeu.core.api.launch.dto.DTOEntity;
import com.moonkeyeu.core.api.launch.dto.ImageDTO;
import com.moonkeyeu.core.api.launch.dto.LocationDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
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
                "latitude",
                "longitude",
                "map_image",
                "location",
                "images"
        })
public class LaunchPadDTO implements DTOEntity {
    @JsonProperty("id")
    private Long launchPadId;
    @JsonProperty("name")
    private String name;
    @JsonProperty("active")
    private Boolean active;
    @JsonProperty("description")
    private String description;
    @JsonProperty("info_url")
    private String infoUrl;
    @JsonProperty("wiki_url")
    private String wikiUrl;
    @JsonProperty("latitude")
    private BigDecimal latitude;
    @JsonProperty("longitude")
    private BigDecimal longitude;
    @JsonProperty("map_image")
    private String mapImage;
    @JsonProperty("location")
    private LocationDTO location;
    @JsonProperty("images")
    private Set<ImageDTO> launchPadImages;

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        LaunchPadDTO that = (LaunchPadDTO) object;
        return Objects.equals(launchPadId, that.launchPadId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(launchPadId);
    }
}
