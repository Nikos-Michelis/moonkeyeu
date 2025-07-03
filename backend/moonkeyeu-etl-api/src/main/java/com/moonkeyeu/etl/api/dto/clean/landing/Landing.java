package com.moonkeyeu.etl.api.dto.clean.landing;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.moonkeyeu.etl.api.dto.clean.CsvEntity;
import lombok.Data;

@Data
@JsonPropertyOrder({"attempt", "success", "description", "downrange_distance", "landing_zone_id", "landing_type_id"})
@JsonIgnoreProperties(ignoreUnknown = true)
public class Landing implements CsvEntity {
    @JsonProperty("landing_id")
    private String landing_id;
    @JsonProperty("attempt")
    private String attempt;
    @JsonProperty("success")
    private String success;
    @JsonProperty("landing_description")
    private String description;
    @JsonProperty("downrange_distance")
    private Integer downrange_distance;
    @JsonProperty("landing_zone_id")
    private Integer landing_zone_id;
    @JsonProperty("type_id")
    private Integer landing_type_id;

    @Override
    public String getPrimaryKey() {
        return landing_id;
    }
}
