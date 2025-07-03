package com.moonkeyeu.etl.api.dto.clean.agency;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.moonkeyeu.etl.api.dto.clean.CsvEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AgenciesTypes implements CsvEntity {
    @JsonProperty("agency_type_id")
    private String type_id;
    @JsonProperty("agency_type")
    private String name;

    @Override
    public String getPrimaryKey() {
        return type_id;
    }
}
