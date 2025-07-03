package com.moonkeyeu.etl.api.dto.json.rocket;

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
public class Rocket implements Serializable {
    @JsonProperty("id")
    private String rocket_id;
    @JsonProperty("configuration")
    private Configuration configuration;
}
