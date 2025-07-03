package com.moonkeyeu.etl.api.dto.clean.mission;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.moonkeyeu.etl.api.dto.clean.CsvEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({"mission_id", "name", "description", "type", "orbit_id"})
@JsonIgnoreProperties(ignoreUnknown = true)
public class Mission implements CsvEntity {
    @JsonProperty("mission_id")
    private String mission_id;
    @JsonProperty("mission_name")
    private String name;
    @JsonProperty("mission_description")
    private String description;
    @JsonProperty("mission_type")
    private String type;
    @JsonProperty("orbit_id")
    private Integer orbit_id;

    @Override
    public String getPrimaryKey() {
        return mission_id;
    }
}