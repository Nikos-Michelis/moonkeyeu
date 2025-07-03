package com.moonkeyeu.etl.api.dto.json.crew;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class LaunchCrew {
    @JsonProperty("id")
    private Integer crew_member_id;
    @JsonProperty("role")
    private Role role;
    @JsonProperty("astronaut")
    private Astronaut astronaut;
    private Integer spacecraft_stage_id;
    private String launch_id;

}

