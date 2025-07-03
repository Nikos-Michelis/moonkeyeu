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
public class Agency {
    @JsonProperty("id")
    private String agency_id;
    @JsonProperty("name")
    private String agency_name;
    @JsonProperty("abbrev")
    private String abbrev;
    @JsonProperty("type")
    private AgenciesTypes agency_type;
    @JsonProperty("featured")
    private Boolean featured;
    @JsonProperty("description")
    private String agency_description;
    @JsonProperty("administrator")
    private String agency_administrator;
    @JsonProperty("founding_year")
    private String founding_year;
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
    @JsonProperty("consecutive_successful_landings")
    private Integer agency_consecutive_successful_landings;
    @JsonProperty("successful_landings")
    private Integer agency_successful_landings;
    @JsonProperty("failed_landings")
    private Integer agency_failed_landings;
    @JsonProperty("attempted_landings")
    private Integer agency_attempted_landings;
    @JsonProperty("info_url")
    private String agency_info_url;
    @JsonProperty("wiki_url")
    private String agency_wiki_url;
    //@JsonProperty("logo")
   // private AgenciesImages image;
    private Integer mission_id;
}
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
class AgenciesImages {
    @JsonProperty("id")
    private Integer agency_image_id;
    @JsonProperty("name")
    private String agency_image_name;
    @JsonProperty("image_url")
    private String agency_image_url;
    @JsonProperty("thumbnail_url")
    private String agency_thumbnail_url;
    @JsonProperty("credit")
    private String agency_credit;
}
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
class AgenciesTypes {
    @JsonProperty("id")
    private Integer agency_type_id;
    @JsonProperty("name")
    private String agency_type;
}
