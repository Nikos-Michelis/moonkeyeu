package com.moonkeyeu.etl.api.dto.clean.rocket;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.moonkeyeu.etl.api.dto.clean.CsvEntity;
import lombok.Data;

@Data
@JsonPropertyOrder({"rocket_id", "rocket_conf_id"})
@JsonIgnoreProperties(ignoreUnknown = true)
public class Rocket implements CsvEntity {
    @JsonProperty( "rocket_id")
    private String rocket_id;
    @JsonProperty( "rocket_conf_id")
    private String rocket_conf_id;

    @Override
    public String getPrimaryKey() {
        return rocket_id;
    }
}
