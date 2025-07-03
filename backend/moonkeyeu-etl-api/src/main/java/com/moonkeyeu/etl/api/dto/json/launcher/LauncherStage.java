package com.moonkeyeu.etl.api.dto.json.launcher;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class LauncherStage {
    private String launch_id;
    @JsonProperty("id")
    private Integer launcher_stage_id;
    @JsonProperty("type")
    private String booster_type;
    private Boolean reused;
    @JsonProperty("launcher")
    private Launcher launcher;
    @JsonProperty("landing")
    private Landing landing;
    @JsonProperty("previous_flight")
    private PreviousFlight previousFlight;
    private Integer rocket_id;
}
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
class PreviousFlight{
    @JsonProperty("id")
    private String prev_launch_id;
    @JsonProperty("name")
    private String prev_rocket_name;
}


