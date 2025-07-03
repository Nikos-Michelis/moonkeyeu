package com.moonkeyeu.etl.api.dto.clean.launch;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.moonkeyeu.etl.api.dto.clean.CsvEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@JsonPropertyOrder({"launch_id", "slug", "flightclub_url", "name", "status_id", "last_updated", "net",
        "probability", "weather_concerns", "agency_id", "rocket_id", "mission_id", "launch_pad_id"})
@JsonIgnoreProperties(ignoreUnknown = true)
public class Launch implements CsvEntity {
    @JsonProperty("launch_id")
    private String launch_id;
    @JsonProperty("slug")
    private String slug;
    @JsonProperty("flightclub_url")
    private String flightclub_url;
    @JsonProperty("launch_name")
    private String name;
    @JsonProperty("last_updated")
    private String last_updated;
    @JsonProperty("net")
    private String net;
    @JsonProperty("window_end")
    private String window_end;
    @JsonProperty("window_start")
    private String window_start;
    @JsonProperty("probability")
    private String probability;
    @JsonProperty("weather_concerns")
    private String weather_concerns;
    @JsonProperty("agency_id")
    private Integer agency_id;
    @JsonProperty("rocket_id")
    private Integer rocket_id;
    @JsonProperty("mission_id")
    private Integer mission_id;
    @JsonProperty("launch_pad_id")
    private Integer launch_pad_id;
    @JsonProperty("status_id")
    private Integer status_id;
    @Override
    public String getPrimaryKey() {
        return launch_id;
    }
}
