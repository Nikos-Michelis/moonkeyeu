package com.moonkeyeu.etl.api.dto.json.launch;

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
public class LaunchStatus implements Serializable {
    @JsonProperty("id")
    private int status_id;
    @JsonProperty("name")
    private String status_name;
    @JsonProperty("abbrev")
    private String status_abbrev;
    @JsonProperty("description")
    private String status_description;
}
