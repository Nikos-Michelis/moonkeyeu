package com.moonkeyeu.etl.api.dto.clean.program;

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
public class ProgramType implements CsvEntity {
    @JsonProperty("type_id")
    private String type_id;
    @JsonProperty("type_name")
    private String type_name;

    @Override
    public String getPrimaryKey() {
        return type_id;
    }
}
