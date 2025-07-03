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
public class LaunchRocketImages implements CsvEntity, ImageEntity {
    @JsonProperty("launch_image_id")
    private String image_id;
    @JsonProperty("launch_image_name")
    private String image_name;
    @JsonProperty("launch_image_url")
    private String image_url;
    @JsonProperty("launch_thumbnail_url")
    private String thumbnail_url;
    @JsonProperty("launch_credit")
    private String credit;
   /* @JsonProperty("rocket_conf_id")
    private String rocket_conf_id;
    @JsonProperty("launch_id")
    private String launch_id;*/

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