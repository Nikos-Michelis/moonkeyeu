package com.moonkeyeu.core.api.launch.dto.agency;

import com.fasterxml.jackson.annotation.*;
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
            "administrator",
            "launchers",
            "spacecraft",
            "country",
            "images",
        })
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AgencySummarizedDTO implements DTOEntity {
    @JsonProperty("id")
    private Long agencyId;
    @JsonProperty("name")
    private String agencyName;
    @JsonProperty("type")
    private String typeName;
    @JsonProperty("administrator")
    private String administrator;
    @JsonProperty("launchers")
    private String launchers;
    @JsonProperty("spacecraft")
    private String spacecraft;
    @JsonProperty("country")
    private Set<CountryDTO> countries;
    @JsonProperty("images")
    private Set<ImageDTO> agenciesImages;
}
