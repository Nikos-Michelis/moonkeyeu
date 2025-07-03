package com.moonkeyeu.etl.api.dto.clean.astronauts;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.moonkeyeu.etl.api.dto.clean.CsvEntity;
import lombok.Data;

@Data
@JsonPropertyOrder({"role_id", "role"})
@JsonIgnoreProperties(ignoreUnknown = true)
public class Roles implements CsvEntity {
    @JsonProperty("role_id")
    private String role_id;
    @JsonProperty("role")
    private String role;
    @Override
    public String getPrimaryKey() {
        return role_id;
    }
}