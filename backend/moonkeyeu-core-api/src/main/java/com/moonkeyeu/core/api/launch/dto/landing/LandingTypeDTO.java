package com.moonkeyeu.core.api.launch.dto.landing;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.moonkeyeu.core.api.launch.dto.DTOEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder(
        {
                "id",
                "name",
                "abbrev",
                "description",
        })
public class LandingTypeDTO implements DTOEntity {
    @JsonProperty("id")
    private Long landingTypeId;
    @JsonProperty("name")
    private String name;
    @JsonProperty("abbrev")
    private String abbrev;
    @JsonProperty("description")
    private String description;

}
