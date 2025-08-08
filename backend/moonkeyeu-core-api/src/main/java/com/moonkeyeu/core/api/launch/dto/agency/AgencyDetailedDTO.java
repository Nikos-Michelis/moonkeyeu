package com.moonkeyeu.core.api.launch.dto.agency;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.moonkeyeu.core.api.launch.dto.CountryDTO;
import com.moonkeyeu.core.api.launch.dto.DTOEntity;
import com.moonkeyeu.core.api.launch.dto.ImageDTO;
import com.moonkeyeu.core.api.launch.dto.launch.LaunchNormalDTO;
import com.moonkeyeu.core.api.launch.dto.spacecraft.SpacecraftConfigNormalDTO;
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
           "type",
           "abbrev",
           "description",
           "launchers",
           "spacecraft",
           "administrator",
           "founding_year",
           "successful_launches",
           "failed_launches",
           "pending_launches",
           "info_url",
           "wiki_url",
           "images",
           "country",
           "upcoming_launch",
           "spacecraft_configurations",
        })
public class AgencyDetailedDTO implements DTOEntity {
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
    @JsonProperty("upcoming_launch")
    private LaunchNormalDTO upcomingLaunches;
    @JsonProperty("spacecraft_configurations")
    private Set<SpacecraftConfigNormalDTO> spacecraftConfigurations;
    @JsonProperty("country")
    private Set<CountryDTO> countries;
    @JsonProperty("images")
    private Set<ImageDTO> agenciesImages;

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        AgencyDetailedDTO that = (AgencyDetailedDTO) object;
        return Objects.equals(agencyId, that.agencyId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(agencyId);
    }
}

