package com.moonkeyeu.etl.api.dto.clean.landing;

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
@JsonPropertyOrder({"type_id", "name", "abbrev", "description"})
@JsonIgnoreProperties(ignoreUnknown = true)
public class LandingType implements CsvEntity {
    @JsonProperty("type_id")
    private String landing_type_id;
    @JsonProperty("type_name")
    private String name;
    @JsonProperty("type_abbrev")
    private String abbrev;
    @JsonProperty("type_description")
    private String description;

    @Override
    public String getPrimaryKey() {
        return landing_type_id;
    }
}