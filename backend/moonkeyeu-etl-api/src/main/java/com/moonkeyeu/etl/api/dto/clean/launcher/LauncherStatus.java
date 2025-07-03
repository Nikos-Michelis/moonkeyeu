package com.moonkeyeu.etl.api.dto.clean.launcher;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.moonkeyeu.etl.api.dto.clean.CsvEntity;
import lombok.Data;

@Data
@JsonPropertyOrder({"status_id", "status_name"})
@JsonIgnoreProperties(ignoreUnknown = true)
public class LauncherStatus implements CsvEntity {
    @JsonProperty("status_id")
    private String status_id;
    @JsonProperty("status_name")
    private String name;

    @Override
    public String getPrimaryKey() {
        return status_id;
    }
}