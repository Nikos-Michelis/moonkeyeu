package com.moonkeyeu.core.api.launch.dto.mission;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.moonkeyeu.core.api.launch.dto.DTOEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({"id", "priority", "name", "image_url"})
public class MissionPatchesDTO implements DTOEntity {
    @JsonProperty("id")
    private Long patchId;
    @JsonProperty("priority")
    private String priority;
    @JsonProperty("name")
    private String name;
    @JsonProperty("image_url")
    private String imageUrl;

}
