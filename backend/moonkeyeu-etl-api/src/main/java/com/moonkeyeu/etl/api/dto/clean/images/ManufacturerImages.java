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
public class ManufacturerImages implements CsvEntity {
     @JsonProperty("manufacturer_image_id")
     private String image_id;
     @JsonProperty("manufacturer_image_name")
     private String image_name;
     @JsonProperty("manufacturer_image_url")
     private String image_url;
     @JsonProperty("manufacturer_thumbnail_url")
     private String thumbnail_url;
     @JsonProperty("manufacturer_credit")
     private String credit;
     @JsonProperty("manufacturer_id")
     private Integer agency_id;


     @Override
     public String getPrimaryKey() {
          return image_id;
     }
}
