package com.moonkeyeu.core.api.launch.dto.launcher;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.moonkeyeu.core.api.launch.dto.DTOEntity;
import com.moonkeyeu.core.api.launch.dto.ImageDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder(
        {
                "id",
                "details",
                "flight_proven",
                "serial_number",
                "successful_landings",
                "attempted_landings",
                "flights",
                "last_launch_date",
                "first_launch_date",
                "status",
                "images"
        })
public class LauncherDTO implements DTOEntity {
    @JsonProperty("id")
    private Long launcherId;
    @JsonProperty("details")
    private String details;
    @JsonProperty("flight_proven")
    private Boolean flightProven;
    @JsonProperty("serial_number")
    private String serialNumber;
    @JsonProperty("successful_landings")
    private Integer successfulLandings;
    @JsonProperty("attempted_landings")
    private Integer attemptedLandings;
    @JsonProperty("flights")
    private Integer flights;
    @JsonProperty("last_launch_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX", timezone = "UTC")
    private Instant lastLaunchDate;
    @JsonProperty("first_launch_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX", timezone = "UTC")
    private Instant firstLaunchDate;
    @JsonProperty("status")
    private String statusName;
    @JsonProperty("images")
    private Set<ImageDTO> launcherImages;

}
