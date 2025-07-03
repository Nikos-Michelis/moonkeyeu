package com.moonkeyeu.etl.api.dto.clean.media;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.moonkeyeu.etl.api.dto.clean.CsvEntity;
import com.moonkeyeu.etl.api.dto.clean.ImageEntity;
import lombok.Data;

@Data
@JsonPropertyOrder({"launch_id", "patch_id", "priority", "name", "image_url"})
@JsonIgnoreProperties(ignoreUnknown = true)
public class MissionPatches implements CsvEntity, ImageEntity {
    private String launch_id;
    @JsonProperty("patch_id")
    private String patch_id;
    @JsonProperty("priority")
    private String priority;
    @JsonProperty("patch_name")
    private String name;
    @JsonProperty("patch_image_url")
    private String image_url;

    @Override
    public String getPrimaryKey() {
        return patch_id;
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
