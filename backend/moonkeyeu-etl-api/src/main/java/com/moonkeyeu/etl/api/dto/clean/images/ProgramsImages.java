package com.moonkeyeu.etl.api.dto.clean.images;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.moonkeyeu.etl.api.dto.clean.CsvEntity;
import com.moonkeyeu.etl.api.dto.clean.ImageEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProgramsImages implements CsvEntity, ImageEntity {
    private String image_id;
    private String image_name;
    private String image_url;
    private String thumbnail_url;
    private String credit;
    private String program_id;

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
