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
public class SpacecraftImages implements CsvEntity, ImageEntity {
    @JsonProperty("spacecraft_image_id")
    private String image_id;
    @JsonProperty("spacecraft_image_name")
    private String image_name;
    @JsonProperty("spacecraft_image_url")
    private String image_url;
    @JsonProperty("spacecraft_thumbnail_url")
    private String thumbnail_url;
    @JsonProperty("spacecraft_credit")
    private String credit;
    @JsonProperty("spacecraft_conf_id")
    private String spacecraft_conf_id;

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
