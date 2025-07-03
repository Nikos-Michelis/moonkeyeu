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
public class InfoUrl implements CsvEntity, PkBuilder {
    private String info_id;
    @JsonProperty("launch_id")
    private String launch_id;
    private Integer priority;
    private String source;
    private String title;
    @JsonProperty("description")
    private String description;
    private String feature_image;
    @JsonProperty("url")
    private String url;

    @Override
    public String getPrimaryKey() {
        setPrimaryKey();
        return info_id + launch_id;
    }

    @Override
    public void setPrimaryKey() {
        this.info_id = priority + launch_id;
    }
}
