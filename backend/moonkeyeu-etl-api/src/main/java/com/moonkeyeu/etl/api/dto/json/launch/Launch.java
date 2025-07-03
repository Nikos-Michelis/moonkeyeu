package com.moonkeyeu.etl.api.dto.json.launch;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.moonkeyeu.etl.api.dto.json.LaunchPad;
import com.moonkeyeu.etl.api.dto.json.agency.Agency;
import com.moonkeyeu.etl.api.dto.json.mission.Mission;
import com.moonkeyeu.etl.api.dto.json.rocket.Rocket;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Launch implements Serializable {
    @JsonProperty("id")
    private String launch_id;
    @JsonProperty("flightclub_url")
    private String flightclub_url;
    @JsonProperty("name")
    private String launch_name;
    @JsonProperty("slug")
    private String slug;
    @JsonProperty("status")
    private LaunchStatus status;
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
    @JsonProperty("launch_service_provider")
    private Agency agency;
    @JsonProperty("rocket")
    private Rocket rocket;
    @JsonProperty("mission")
    private Mission mission;
    @JsonProperty("pad")
    private LaunchPad pad;
    @JsonProperty("image")
    private RocketImages image;
    @JsonProperty("webcast_live")
    private Boolean webcast_live;
}
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
class RocketImages {
    @JsonProperty("id")
    private Integer launch_image_id;
    @JsonProperty("name")
    private String launch_image_name;
    @JsonProperty("image_url")
    private String launch_image_url;
    @JsonProperty("thumbnail_url")
    private String launch_thumbnail_url;
    @JsonProperty("credit")
    private String launch_credit;
}
