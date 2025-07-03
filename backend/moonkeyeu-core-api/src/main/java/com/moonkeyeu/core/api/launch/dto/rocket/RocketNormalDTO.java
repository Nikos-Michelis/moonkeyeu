package com.moonkeyeu.core.api.launch.dto.rocket;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.moonkeyeu.core.api.launch.dto.launch.LaunchSummarizedDTO;
import com.moonkeyeu.core.api.launch.dto.DTOEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({"id", "configuration", "launches"})
public class RocketNormalDTO implements DTOEntity {
    @JsonProperty("id")
    private Integer rocketId;
    @JsonProperty("configuration")
    private RocketConfigurationDTO rocketConfiguration;
    @JsonProperty("launches")
    private Set<LaunchSummarizedDTO> launches;
}
