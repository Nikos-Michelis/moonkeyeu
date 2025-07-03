package com.moonkeyeu.core.api.launch.dto.launch;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.moonkeyeu.core.api.launch.dto.ImageDTO;
import com.moonkeyeu.core.api.launch.dto.agency.AgencyNormalDTO;
import com.moonkeyeu.core.api.launch.dto.pad.LaunchPadDTO;
import com.moonkeyeu.core.api.launch.dto.DTOEntity;
import com.moonkeyeu.core.api.launch.dto.info.VideoDTO;
import com.moonkeyeu.core.api.launch.dto.mission.MissionDTO;
import com.moonkeyeu.core.api.launch.dto.mission.MissionPatchesDTO;
import com.moonkeyeu.core.api.launch.dto.program.ProgramSummarizedDTO;
import com.moonkeyeu.core.api.launch.dto.rocket.RocketDetailedDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Objects;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder(
        {
                "id",
                "status",
                "flightclub_url",
                "fullname",
                "lastUpdated",
                "net",
                "probability",
                "launch_provider",
                "rocket",
                "mission",
                "pad",
        })
public class LaunchDTO implements DTOEntity {
    @JsonProperty("id")
    private String launchId;
    @JsonProperty("status")
    private String statusName;
    @JsonProperty("flightclub_url")
    private String flightclubUrl;
    @JsonProperty("fullname")
    private String launchName;
    @JsonProperty("lastUpdated")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX", timezone = "UTC")
    private Instant lastUpdated;
    @JsonProperty("net")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX", timezone = "UTC")
    private Instant net;
    @JsonProperty("window_start")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssX", timezone = "UTC")
    private Instant windowStart;
    @JsonProperty("window_end")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssX", timezone = "UTC")
    private Instant windowEnd;
    @JsonProperty("probability")
    private Integer probability;
    @JsonProperty("launch_provider")
    private AgencyNormalDTO agencies;
    @JsonProperty("rocket")
    private RocketDetailedDTO rocket;
    @JsonProperty("mission")
    private MissionDTO mission;
    @JsonProperty("pad")
    private LaunchPadDTO launchPad;
    @JsonProperty("image")
    private ImageDTO rocketConfImages;
    @JsonProperty("programs")
    private Set<ProgramSummarizedDTO> programs;
    @JsonProperty("mission_patches")
    private Set<MissionPatchesDTO> missionPatches;
    @JsonProperty("video_urls")
    private Set<VideoDTO> videoUrls;

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        LaunchDTO launchDTO = (LaunchDTO) object;
        return Objects.equals(launchId, launchDTO.launchId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(launchId);
    }
}
