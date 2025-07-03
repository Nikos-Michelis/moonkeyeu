package com.moonkeyeu.etl.api.dto.clean.spacecraft;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.moonkeyeu.etl.api.dto.clean.CsvEntity;
import lombok.Data;

@Data
@JsonPropertyOrder({"spacecraft_id", "name", "serial_number", "is_placeholder", "in_space", "flights_count", "mission_ends_count", "description", "spacecraft_config_id", "status_id"})
@JsonIgnoreProperties(ignoreUnknown = true)
public class Spacecraft implements CsvEntity {
    @JsonProperty("spacecraft_id")
    private String spacecraft_id;
    @JsonProperty("spacecraft_name")
    private String name;
    @JsonProperty("serial_number")
    private String serial_number;
    @JsonProperty("is_placeholder")
    private String is_placeholder;
    @JsonProperty("in_space")
    private String in_space;
    @JsonProperty("flights_count")
    private Integer flights_count;
    @JsonProperty("mission_ends_count")
    private Integer mission_ends_count;
    @JsonProperty("spacecraft_description")
    private String description;
    @JsonProperty("spacecraft_conf_id")
    private Integer spacecraft_conf_id;
    @JsonProperty("status_id")
    private Integer status_id;

    @Override
    public String getPrimaryKey() {
        return spacecraft_id;
    }
}
