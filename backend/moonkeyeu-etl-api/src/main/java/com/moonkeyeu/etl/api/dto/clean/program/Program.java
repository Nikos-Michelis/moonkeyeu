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
public class Program implements CsvEntity {
    @JsonProperty("program_id")
    private String program_id;
    private String name;
    private String info_url;
    private String wiki_url;
    private String description;
    private String start_date;
    @JsonProperty("type_id")
    private Integer type_id;
    @JsonProperty("launch_id")
    private String launch_id;

    @Override
    public String getPrimaryKey() {
        return program_id;
    }
}
