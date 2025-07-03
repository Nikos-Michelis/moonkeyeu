package com.moonkeyeu.etl.api.dto.clean.images;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.moonkeyeu.etl.api.dto.clean.CsvEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PadImages implements CsvEntity {
    @JsonProperty("pad_image_id")
    private String image_id;
    @JsonProperty("pad_image_name")
    private String image_name;
    @JsonProperty("pad_image_url")
    private String image_url;
    @JsonProperty("pad_thumbnail_url")
    private String thumbnail_url;
    @JsonProperty("pad_credit")
    private String credit;
    @JsonProperty("launch_pad_id")
    private Integer launch_pad_id;

    @Override
    public String getPrimaryKey() {
        return image_id;
    }
}

