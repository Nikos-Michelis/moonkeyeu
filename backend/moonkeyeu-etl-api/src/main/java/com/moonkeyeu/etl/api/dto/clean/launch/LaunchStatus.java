package com.moonkeyeu.etl.api.dto.clean.launch;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.moonkeyeu.etl.api.dto.clean.CsvEntity;
import lombok.Data;

@Data
@JsonPropertyOrder({"status_id", "name", "abbrev", "description"})
@JsonIgnoreProperties(ignoreUnknown = true)
public class LaunchStatus implements CsvEntity {
    @JsonProperty("status_id")
    private String status_id;
    @JsonProperty("status_name")
    private String name;
    @JsonProperty("status_abbrev")
    private String abbrev;
    @JsonProperty("status_description")
    private String description;


    @Override
    public String getPrimaryKey() {
        return status_id;
    }
}
