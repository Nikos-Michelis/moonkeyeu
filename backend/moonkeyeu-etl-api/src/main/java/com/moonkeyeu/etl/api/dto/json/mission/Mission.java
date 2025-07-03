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
public class Mission implements Serializable {
    @JsonProperty("id")
    private Integer mission_id;
    @JsonProperty("name")
    private String mission_name;
    @JsonProperty("description")
    private String mission_description;
    @JsonProperty("type")
    private String mission_type;
    private Orbit orbit;
}


