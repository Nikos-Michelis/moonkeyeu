package com.moonkeyeu.etl.api.dto.clean.launcher;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.moonkeyeu.etl.api.dto.clean.CsvEntity;
import lombok.Data;

@Data
@JsonPropertyOrder({"launcher_stage_id", "type", "reused", "rocket_id", "launcher_id", "landing_id"})
@JsonIgnoreProperties(ignoreUnknown = true)
public class LauncherStage implements CsvEntity {
    @JsonProperty("launcher_stage_id")
    private String launcher_stage_id;
    @JsonProperty("booster_type")
    private String type;
    @JsonProperty("reused")
    private String reused;
    @JsonProperty("rocket_id")
    private Integer rocket_id;
    @JsonProperty("launcher_id")
    private Integer launcher_id;
    @JsonProperty("landing_id")
    private Integer landing_id;

    @Override
    public String getPrimaryKey() {
        return launcher_stage_id;
    }
}

