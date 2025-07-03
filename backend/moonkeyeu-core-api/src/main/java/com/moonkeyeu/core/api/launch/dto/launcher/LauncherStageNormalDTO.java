package com.moonkeyeu.core.api.launch.dto.launcher;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.moonkeyeu.core.api.launch.dto.landing.LandingDTO;
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
                "type",
                "reused",
                "launcher",
                "landing",
        })
public class LauncherStageNormalDTO implements DTOEntity {
    @JsonProperty("id")
    private Long launcherStageId;
    @JsonProperty("type")
    private String type;
    @JsonProperty("reused")
    private Boolean reused;
    @JsonProperty("launcher")
    private LauncherDTO launcher;
    @JsonProperty("landing")
    private LandingDTO landing;
}
