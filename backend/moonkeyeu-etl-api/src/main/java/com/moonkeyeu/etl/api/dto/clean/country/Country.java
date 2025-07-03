package com.moonkeyeu.etl.api.dto.clean.country;

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
public class Country implements CsvEntity {
    @JsonProperty("country_id")
    private String country_id;
    private String name;
    private String alpha_2_code;
    private String alpha_3_code;
    private String nationality_name;
    private String nationality_name_composed;
    @Override
    public String getPrimaryKey() {
        return country_id;
    }
}
