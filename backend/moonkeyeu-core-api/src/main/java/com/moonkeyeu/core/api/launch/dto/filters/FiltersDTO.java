package com.moonkeyeu.core.api.launch.dto.filters;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.moonkeyeu.core.api.launch.dto.DTOEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder(
        {
                "id",
                "name",
        })
public class FiltersDTO implements DTOEntity {
    @JsonProperty("id")
    private Integer filterId;
    @JsonProperty("name")
    private String filterName;
}
