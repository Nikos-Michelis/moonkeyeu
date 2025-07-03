package com.moonkeyeu.core.api.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.moonkeyeu.core.api.launch.dto.DTOEntity;
import com.moonkeyeu.core.api.launch.dto.ImageDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookmarkDTO implements DTOEntity {
    @JsonProperty("id")
    private Long bookmarkId;
    @JsonProperty("bookmark")
    private String bookmarkName;
    @JsonProperty("launches")
    private Set<LaunchIds> launches;
}
@Data
@AllArgsConstructor
@NoArgsConstructor
class LaunchIds implements DTOEntity{
    @JsonProperty("id")
    private String launchId;
    @JsonProperty("image")
    private ImageDTO rocketConfImages;
}
