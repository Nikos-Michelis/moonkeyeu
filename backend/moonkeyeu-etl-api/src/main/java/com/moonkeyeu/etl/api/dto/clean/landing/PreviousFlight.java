package com.moonkeyeu.etl.api.dto.clean.landing;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.moonkeyeu.etl.api.dto.clean.CsvEntity;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class PreviousFlight implements CsvEntity {
    @JsonProperty("prev_launch_id")
    private String launch_id;
    @JsonProperty("prev_rocket_name")
    private String name;

    @Override
    public String getPrimaryKey() {
        return launch_id;
    }
}