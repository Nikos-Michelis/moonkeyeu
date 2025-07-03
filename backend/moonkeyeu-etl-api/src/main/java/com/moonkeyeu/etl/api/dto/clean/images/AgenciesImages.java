package com.moonkeyeu.etl.api.dto.clean.images;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.moonkeyeu.etl.api.dto.clean.CsvEntity;
import com.moonkeyeu.etl.api.dto.clean.ImageEntity;
import com.moonkeyeu.etl.api.dto.json.Images.Image;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AgenciesImages implements CsvEntity, ImageEntity {
    @JsonProperty("image_id")
    private String image_id;
    @JsonProperty("image_name")
    private String image_name;
    @JsonProperty("image_url")
    private String image_url;
    @JsonProperty("thumbnail_url")
    private String thumbnail_url;
    @JsonProperty("credit")
    private String credit;
    @JsonProperty("agency_id")
    private Integer agency_id;

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
