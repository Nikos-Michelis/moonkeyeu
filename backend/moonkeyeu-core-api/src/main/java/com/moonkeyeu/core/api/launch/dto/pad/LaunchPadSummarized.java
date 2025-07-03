package com.moonkeyeu.core.api.launch.dto.pad;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.moonkeyeu.core.api.launch.dto.DTOEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LaunchPadSummarized implements DTOEntity {
    @JsonProperty("location_id")
    private Long locationId;
    @JsonProperty("name")
    private String locationName;
}
