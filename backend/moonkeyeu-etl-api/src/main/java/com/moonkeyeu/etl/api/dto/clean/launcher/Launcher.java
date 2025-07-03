package com.moonkeyeu.etl.api.dto.clean.launcher;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.moonkeyeu.etl.api.dto.clean.CsvEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({"launcher_id", "details", "flight_proven", "serial_number", "successful_landings", "attempted_landings", "flights",
        "last_launch_date", "first_launch_date", "status_id"})
@JsonIgnoreProperties(ignoreUnknown = true)
public class Launcher implements CsvEntity {
    @JsonProperty("launcher_id")
    private String launcher_id;
    @JsonProperty("details")
    private String details;
    @JsonProperty("flight_proven")
    private String flight_proven;
    @JsonProperty("serial_number")
    private String serial_number;
    @JsonProperty("launcher_successful_landings")
    private Integer successful_landings;
    @JsonProperty("attempted_landings")
    private Integer attempted_landings;
    @JsonProperty("flights")
    private Integer flights;
    @JsonProperty("last_launch_date")
    private String last_launch_date;
    @JsonProperty("first_launch_date")
    private String first_launch_date;
    @JsonProperty("status_id")
    private String status_id;

    @Override
    public String getPrimaryKey() {
        return launcher_id;
    }
}
