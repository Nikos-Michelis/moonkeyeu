package com.moonkeyeu.etl.api.dto.clean.spacecraft;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.moonkeyeu.etl.api.dto.clean.CsvEntity;
import lombok.Data;

@Data
@JsonPropertyOrder({"type_id", "name", "destination"})
@JsonIgnoreProperties(ignoreUnknown = true)
public class SpaceCraftType implements CsvEntity {
    @JsonProperty("spacecraft_type_id")
    private String type_id;
    @JsonProperty("spacecraft_type")
    private String name;

    @Override
    public String getPrimaryKey() {
        return type_id;
    }
}
