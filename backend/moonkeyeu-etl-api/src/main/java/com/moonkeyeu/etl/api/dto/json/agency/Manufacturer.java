package com.moonkeyeu.etl.api.dto.json.agency;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Manufacturer {
     @JsonProperty("id")
     private Integer manufacturer_id;
     @JsonProperty("name")
     private String manufacturer_name;
     @JsonProperty("type")
     private ManufacturerTypes manufacturer_type;
     //@JsonProperty("country")
    // private List<Country> country;
     @JsonProperty("abbrev")
     private String manufacturer_abbrev;
     @JsonProperty("description")
     private String manufacturer_description;
     @JsonProperty("administrator")
     private String manufacturer_administrator;
     @JsonProperty("founding_year")
     private int manufacturer_founding_year;
     @JsonProperty("launchers")
     private String manufacturer_launchers;
     @JsonProperty("spacecraft")
     private String manufacturer_spacecraft;
     @JsonProperty("total_launch_count")
     private Integer manufacturer_total_launch_count;
     @JsonProperty("consecutive_successful_launches")
     private Integer manufacturer_consecutive_successful_launches;
     @JsonProperty("successful_launches")
     private Integer manufacturer_successful_launches;
     @JsonProperty("failed_launches")
     private Integer manufacturer_failed_launches;
     @JsonProperty("pending_launches")
     private Integer manufacturer_pending_launches;
     @JsonProperty("consecutive_successful_landings")
     private Integer manufacturer_consecutive_successful_landings;
     @JsonProperty("successful_landings")
     private Integer manufacturer_successful_landings;
     @JsonProperty("failed_landings")
     private Integer manufacturer_failed_landings;
     @JsonProperty("attempted_landings")
     private Integer manufacturer_attempted_landings;
     @JsonProperty("info_url")
     private String manufacturer_info_url;
     @JsonProperty("wiki_url")
     private String manufacturer_wiki_url;
     @JsonProperty("logo")
     private ManufacturerImages image;
}
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
class ManufacturerImages {
     @JsonProperty("id")
     private Integer manufacturer_image_id;
     @JsonProperty("name")
     private String manufacturer_image_name;
     @JsonProperty("image_url")
     private String manufacturer_image_url;
     @JsonProperty("thumbnail_url")
     private String manufacturer_thumbnail_url;
     @JsonProperty("credit")
     private String manufacturer_credit;
}
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
class ManufacturerTypes {
     @JsonProperty("id")
     private Integer manufacturer_type_id;
     @JsonProperty("name")
     private String manufacturer_type;
}