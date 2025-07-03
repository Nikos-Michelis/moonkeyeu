package com.moonkeyeu.etl.api.dto.clean.agency;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.moonkeyeu.etl.api.dto.clean.CsvEntity;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Agency implements CsvEntity {
     @JsonProperty("agency_id")
     private String agency_id;
     @JsonProperty("agency_name")
     private String name;
     @JsonProperty("agency_type_id")
     private Integer type_id;
     @JsonProperty("featured")
     private Boolean featured;
     @JsonProperty("abbrev")
     private String abbrev;
     @JsonProperty("agency_description")
     private String description;
     @JsonProperty("agency_administrator")
     private String administrator;
     @JsonProperty("founding_year")
     private Integer founding_year;
     @JsonProperty("launchers")
     private String launchers;
     @JsonProperty("spacecraft")
     private String spacecraft;
     @JsonProperty("total_launch_count")
     private Integer total_launch_count;
     @JsonProperty("consecutive_successful_launches")
     private Integer consecutive_successful_launches;
     @JsonProperty("successful_launches")
     private Integer successful_launches;
     @JsonProperty("failed_launches")
     private Integer failed_launches;
     @JsonProperty("pending_launches")
     private Integer pending_launches;
     @JsonProperty("agency_consecutive_successful_landings")
     private Integer consecutive_successful_landings;
     @JsonProperty("agency_successful_landings")
     private Integer successful_landings;
     @JsonProperty("agency_failed_landings")
     private Integer failed_landings;
     @JsonProperty("agency_attempted_landings")
     private Integer attempted_landings;
     @JsonProperty("agency_info_url")
     private String info_url;
     @JsonProperty("agency_wiki_url")
     private String wiki_url;

     @Override
     public String getPrimaryKey() {
          return agency_id;
     }
}
