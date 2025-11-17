package com.moonkeyeu.core.api.launch.dto.celestial;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder(
        {
                "id",
                "type",
                "name",
                "mass",
                "diameter",
                "gravity",
                "tilt",
                "rotation",
                "orbit",
                "distance",
                "moons",
                "atmosphere",
                "description",
                "img_day",
                "img_night",
                "img_atmosphere",
                "img_ring",
        })
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class CelestialBodyDTO {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("type")
    private String typeName;
    private PlanetValueDTO mass;
    private PlanetValueDTO diameter;
    private PlanetValueDTO gravity;
    private PlanetValueDTO tilt;
    private PlanetValueDTO rotation;
    private PlanetValueDTO orbit;
    private PlanetValueDTO distance;
    private Integer moons;
    private Boolean atmosphere;
    private String description;
    @JsonProperty("img_day")
    private String imgDay;
    @JsonProperty("img_night")
    private String imgNight;
    @JsonProperty("img_atmosphere")
    private String imgAtmosphere;
    @JsonProperty("img_ring")
    private String imgRing;
}
