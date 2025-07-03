package com.moonkeyeu.etl.api.dto.json.mission;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Orbit implements Serializable {
    @JsonProperty("id")
    private String orbit_id;
    @JsonProperty("name")
    private String orbit_name;
    @JsonProperty("abbrev")
    private String orbit_abbrev;
}