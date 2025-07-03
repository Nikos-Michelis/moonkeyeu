package com.moonkeyeu.core.api.launch.dto.info;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({"videoId", "priority", "source", "publisher", "title", "videoUrl"})
public class VideoDTO {
    @JsonProperty("id")
    private String videoId;
    private Integer priority;
    private String source;
    private String publisher;
    private String title;
    private String videoUrl;
}
