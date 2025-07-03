package com.moonkeyeu.core.api.launch.dto.rocket;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.moonkeyeu.core.api.launch.dto.DTOEntity;
import com.moonkeyeu.core.api.launch.dto.ImageDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder(
        {
                "id",
                "name",
                "variant",
                "fullname",
                "active",
                "reusable",
                "description",
                "alias",
                "min_stage",
                "max_stage",
                "maiden_flight",
                "length",
                "diameter",
                "launch_cost",
                "launch_mass",
                "leo_capacity",
                "gto_capacity",
                "geo_capacity",
                "sso_capacity",
                "to_thrust",
                "apogee",
                "infoUrl",
                "wikiUrl",
                "images",
        })
public class RocketConfigurationDTO implements DTOEntity {
    @JsonProperty("id")
    private Long rocketConfId;
    @JsonProperty("name")
    private String rocketName;
    @JsonProperty("variant")
    private String variant;
    @JsonProperty("fullname")
    private String fullname;
    @JsonProperty("active")
    private Boolean active;
    @JsonProperty("reusable")
    private Boolean reusable;
    @JsonProperty("description")
    private String description;
    @JsonProperty("alias")
    private String alias;
    @JsonProperty("min_stage")
    private Integer minStage;
    @JsonProperty("max_stage")
    private Integer maxStage;
    @JsonProperty("maiden_flight")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate maidenFlight;
    @JsonProperty("length")
    private Double length;
    @JsonProperty("diameter")
    private Double diameter;
    @JsonProperty("launch_cost")
    private Double launchCost;
    @JsonProperty("launch_mass")
    private Double launchMass;
    @JsonProperty("leo_capacity")
    private Double leoCapacity;
    @JsonProperty("gto_capacity")
    private Double gtoCapacity;
    @JsonProperty("geo_capacity")
    private Double geoCapacity;
    @JsonProperty("sso_capacity")
    private Double ssoCapacity;
    @JsonProperty("to_thrust")
    private Integer toThrust;
    @JsonProperty("apogee")
    private Integer apogee;
    @JsonProperty("infoUrl")
    private String info_url;
    @JsonProperty("wikiUrl")
    private String wiki_url;
    @JsonProperty("image")
    private ImageDTO rocketConfImages;

}
