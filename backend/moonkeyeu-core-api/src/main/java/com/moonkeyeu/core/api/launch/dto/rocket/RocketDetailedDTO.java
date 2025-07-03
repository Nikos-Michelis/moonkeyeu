package com.moonkeyeu.core.api.launch.dto.rocket;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.moonkeyeu.core.api.launch.dto.DTOEntity;
import com.moonkeyeu.core.api.launch.dto.launcher.LauncherStageNormalDTO;
import com.moonkeyeu.core.api.launch.dto.spacecraft.SpacecraftStageDTO;
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
                "configuration",
                "spacecraft_stage",
                "launcher_stage"
        })
public class RocketDetailedDTO implements DTOEntity {
    @JsonProperty("id")
    private Integer rocketId;
    @JsonProperty("configuration")
    private RocketConfigurationDTO rocketConfiguration;
    @JsonProperty("spacecraft_stage")
    private Set<SpacecraftStageDTO> spacecraftStages;
    @JsonProperty("launcher_stage")
    private Set<LauncherStageNormalDTO> launcherStages;
}
