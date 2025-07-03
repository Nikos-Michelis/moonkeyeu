package com.moonkeyeu.etl.api.dto.json.launcher;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.moonkeyeu.etl.api.dto.json.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Launcher {
    @JsonProperty("id")
    private Integer launcher_id;
    private Boolean flight_proven;
    private String serial_number;
    private Status status;
    @JsonProperty("image")
    private LauncherImages image;
    private String details;
    @JsonProperty("successful_landings")
    private Integer launcher_successful_landings;
    private Integer attempted_landings;
    private Integer flights;
    private String last_launch_date;
    private String first_launch_date;
}
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
class LauncherImages {
    @JsonProperty("id")
    private Integer launcher_image_id;
    @JsonProperty("name")
    private String launcher_image_name;
    @JsonProperty("image_url")
    private String launcher_image_url;
    @JsonProperty("thumbnail_url")
    private String launcher_thumbnail_url;
    @JsonProperty("credit")
    private String launcher_credit;
}