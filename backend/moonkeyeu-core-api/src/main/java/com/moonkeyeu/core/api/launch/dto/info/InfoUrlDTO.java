package com.moonkeyeu.core.api.launch.dto.info;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder(
        {
                "infoId",
                "priority",
                "source",
                "title",
                "url"
        })
public class InfoUrlDTO {
    @JsonProperty("id")
    private String infoId;
    private Integer priority;
    private String source;
    private String title;
    private String url;
}
