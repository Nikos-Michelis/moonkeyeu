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
                "maiden_flight",
                "agency",
                "capability",
                "history",
                "details",
                "wiki_url",
                "info_url",
                "images"
        })
public class SpacecraftConfigSummarizedDTO implements DTOEntity {
    @JsonProperty("id")
    private Long spacecraftConfId;
    @JsonProperty("name")
    private String spacecraftConfName;
    @JsonProperty("type")
    private String typeName;
    @JsonProperty("agency")
    private AgencySortDTO agencies;
    @JsonProperty("capability")
    private String capability;
    @JsonProperty("history")
    private String history;
    @JsonProperty("details")
    private String details;
    @JsonProperty("maiden_flight")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate maidenFlight;
    @JsonProperty("wiki_url")
    private String wikiLink;
    @JsonProperty("info_url")
    private String infoLink;
    @JsonProperty("images")
    private Set<ImageDTO> spacecraftConfImages;
}
@Data
@AllArgsConstructor
@NoArgsConstructor
class AgencySortDTO {
    @JsonProperty("name")
    private String agencyName;
}

