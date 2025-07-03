package com.moonkeyeu.etl.api.dto.clean.spacecraft;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.moonkeyeu.etl.api.dto.clean.CsvEntity;
import lombok.Data;

@Data
@JsonPropertyOrder({"spacecraft_stage_id", "mission_end", "destination", "rocket_id", "spacecraft_id", "landing_id"})
@JsonIgnoreProperties(ignoreUnknown = true)
public class SpaceCraftStage implements CsvEntity {
    @JsonProperty("spacecraft_stage_id")
    private String spacecraft_stage_id;
    @JsonProperty("mission_end")
    private String mission_end;
    @JsonProperty("destination")
    private String  destination;
    @JsonProperty("rocket_id")
    private Integer rocket_id;
    @JsonProperty("spacecraft_id")
    private Integer spacecraft_id;
    @JsonProperty("landing_id")
    private Integer landing_id;

    @Override
    public String getPrimaryKey() {
        return spacecraft_stage_id;
    }
}
