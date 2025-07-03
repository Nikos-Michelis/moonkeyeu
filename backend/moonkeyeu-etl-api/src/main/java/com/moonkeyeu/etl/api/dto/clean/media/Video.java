package com.moonkeyeu.etl.api.dto.clean.media;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.moonkeyeu.etl.api.dto.clean.CsvEntity;
import com.moonkeyeu.etl.api.dto.clean.PkBuilder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Video implements CsvEntity, PkBuilder {
    private String video_id;
    @JsonProperty("launch_id")
    private String launch_id;
    private Integer priority;
    private String source;
    private String publisher;
    private String title;
    private String description;
    private String feature_image;
    @JsonProperty("video_url")
    private String video_url;

    @Override
    public String getPrimaryKey() {
        setPrimaryKey();
        return video_id + launch_id;
    }

    @Override
    public void setPrimaryKey() {
        this.video_id = priority + launch_id;
    }
}
