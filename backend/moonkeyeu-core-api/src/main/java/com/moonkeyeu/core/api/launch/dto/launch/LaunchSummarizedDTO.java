package com.moonkeyeu.core.api.launch.dto.launch;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.moonkeyeu.core.api.launch.dto.info.InfoUrlDTO;
import com.moonkeyeu.core.api.launch.dto.DTOEntity;
import com.moonkeyeu.core.api.launch.dto.ImageDTO;
import com.moonkeyeu.core.api.launch.dto.info.VideoDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({"id", "fullname", "net", "windowStart", "windowEnd", "status", "location", "agency", "image"})
public class LaunchSummarizedDTO implements DTOEntity {
    @JsonProperty("id")
    private String launchId;
    @JsonProperty("fullname")
    private String launchName;
    @JsonProperty("agency")
    private String agenciesName;
    @JsonProperty("net")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssX", timezone = "UTC")
    private Instant net;
    @JsonProperty("status")
    private String statusName;
    @JsonProperty("image")
    private ImageDTO rocketConfImages;
    @JsonProperty("video_urls")
    private Set<VideoDTO> videoUrls;
    @JsonProperty("info_urls")
    private Set<InfoUrlDTO> infoUrls;
}