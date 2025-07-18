package com.moonkeyeu.core.api.launch.dto.agency;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.moonkeyeu.core.api.launch.dto.CountryDTO;
import com.moonkeyeu.core.api.launch.dto.DTOEntity;
import com.moonkeyeu.core.api.launch.dto.ImageDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder(
        {
                "id",
                "name",
                "type",
                "abbrev",
                "administrator",
                "description",
                "founding_year",
                "successful_launches",
                "failed_launches",
                "pending_launches",
                "info_url",
                "wiki_url",
                "images",
                "country",
        })
public class AgencyNormalDTO implements DTOEntity {
    @JsonProperty("id")
    private Long agencyId;
    @JsonProperty("name")
    private String agencyName;
    @JsonProperty("type")
    private String typeName;
    @JsonProperty("abbrev")
    private String abbrev;
    @JsonProperty("administrator")
    private String administrator;
    @JsonProperty("description")
    private String description;
    @JsonProperty("launchers")
    private String launchers;
    @JsonProperty("spacecraft")
    private String spacecraft;
    @JsonProperty("founding_year")
    private String foundingYear;
    @JsonProperty("successful_launches")
    private Integer successfulLaunches;
    @JsonProperty("failed_launches")
    private Integer failedLaunches;
    @JsonProperty("pending_launches")
    private Integer pendingLaunches;
    @JsonProperty("info_url")
    private String infoUrl;
    @JsonProperty("wiki_url")
    private String wikiUrl;
    @JsonProperty("images")
    private Set<ImageDTO> agenciesImages;
    @JsonProperty("country")
    private Set<CountryDTO> countries;
}
