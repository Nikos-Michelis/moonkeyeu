package com.moonkeyeu.etl.api.dto.clean.images;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.moonkeyeu.etl.api.dto.clean.CsvEntity;
import com.moonkeyeu.etl.api.dto.clean.ImageEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AstronautImages implements CsvEntity, ImageEntity {
    @JsonProperty("astronaut_image_id")
    private String image_id;
    @JsonProperty("astronaut_image_name")
    private String image_name;
    @JsonProperty("astronaut_image_url")
    private String image_url;
    @JsonProperty("astronaut_thumbnail_url")
    private String thumbnail_url;
    @JsonProperty("astronaut_credit")
    private String credit;
    @JsonProperty("astronaut_id")
    private String astronaut_id;

    @Override
    public String getPrimaryKey() {
        return image_id;
    }

    @Override
    public String getImageUrl() {
        return image_url;
    }

    @Override
    public void setImageUrl(String imageUrl) {
        this.image_url = imageUrl;
    }
}