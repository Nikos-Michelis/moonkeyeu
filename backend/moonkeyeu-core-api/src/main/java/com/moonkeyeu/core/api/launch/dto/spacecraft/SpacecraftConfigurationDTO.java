package com.moonkeyeu.core.api.launch.dto.spacecraft;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.moonkeyeu.core.api.launch.dto.DTOEntity;
import com.moonkeyeu.core.api.launch.dto.ImageDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder(
        {
                "id",
                "name",
                "type",
                "in_use",
                "capability",
                "history",
                "details",
                "maiden_flight",
                "height",
                "diameter",
                "human_rated",
                "crew_capacity",
                "payload_capacity",
                "payload_return_capacity",
                "flight_life",
                "wiki_url",
                "info_url",
                "agency",
                "images"
        })
public class SpacecraftConfigurationDTO implements DTOEntity {
    @JsonProperty("id")
    private Long spacecraftConfId;
    @JsonProperty("name")
    private String spacecraftConfName;
    @JsonProperty("type")
    private String typeName;
    @JsonProperty("in_use")
    private Boolean inUse;
    @JsonProperty("capability")
    private String capability;
    @JsonProperty("history")
    private String history;
    @JsonProperty("details")
    private String details;
    @JsonProperty("maiden_flight")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate maidenFlight;
    @JsonProperty("height")
    private Double height;
    @JsonProperty("diameter")
    private Double diameter;
    @JsonProperty("human_rated")
    private Boolean humanRated;
    @JsonProperty("crew_capacity")
    private Integer crewCapacity;
    @JsonProperty("payload_capacity")
    private Integer payloadCapacity;
    @JsonProperty("payload_return_capacity")
    private Integer payloadReturnCapacity;
    @JsonProperty("flight_life")
    private String flightLife;
    @JsonProperty("wiki_url")
    private String wikiLink;
    @JsonProperty("info_url")
    private String infoLink;
    @JsonProperty("images")
    private Set<ImageDTO> spacecraftConfImages;
}