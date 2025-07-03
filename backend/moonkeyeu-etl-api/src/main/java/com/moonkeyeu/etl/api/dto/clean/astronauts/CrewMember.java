package com.moonkeyeu.etl.api.dto.clean.astronauts;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.moonkeyeu.etl.api.dto.clean.CsvEntity;
import lombok.Data;

@Data
@JsonPropertyOrder({"crew_member_id", "astronaut_id", "role_id", "crew_group_id", "launch_id"})
@JsonIgnoreProperties(ignoreUnknown = true)
public class CrewMember implements CsvEntity {
    @JsonProperty("crew_member_id")
    private String crew_member_id;
    @JsonProperty("astronaut_id")
    private Integer astronaut_id;
    @JsonProperty("role_id")
    private Integer role_id;
    @JsonProperty("spacecraft_stage_id")
    private Integer spacecraft_stage_id;
    @JsonProperty("launch_id")
    private String launch_id;

    @Override
    public String getPrimaryKey() {
        return crew_member_id;
    }
}
