package com.moonkeyeu.core.api.launch.dto.astronaut;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.moonkeyeu.core.api.launch.dto.DTOEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({"id", "role", "astronaut"})
public class CrewMemberDTO implements DTOEntity {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("astronaut")
    private AstronautNormalDTO astronaut;
    @JsonProperty("role")
    private String roleName;
}
