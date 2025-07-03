package com.moonkeyeu.etl.api.dto.clean.mission;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.moonkeyeu.etl.api.dto.clean.CsvEntity;
import lombok.Data;

@Data
@JsonPropertyOrder({"orbit_id", "name", "abbrev"})
@JsonIgnoreProperties(ignoreUnknown = true)
public class Orbit implements CsvEntity {
    @JsonProperty("orbit_id")
    private String orbit_id;
    @JsonProperty("orbit_name")
    private String name;
    @JsonProperty("orbit_abbrev")
    private String abbrev;

    @Override
    public String getPrimaryKey() {
        return orbit_id;
    }
}
