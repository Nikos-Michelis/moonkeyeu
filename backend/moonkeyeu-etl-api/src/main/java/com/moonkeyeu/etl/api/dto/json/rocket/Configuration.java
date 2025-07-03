package com.moonkeyeu.etl.api.dto.json.rocket;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.moonkeyeu.etl.api.dto.json.agency.Manufacturer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Configuration implements Serializable {
    @JsonProperty("id")
    private Integer rocket_conf_id;
    @JsonProperty("name")
    private String rocket_name;
    @JsonProperty("active")
    private Boolean active;
    @JsonProperty("reusable")
    private Boolean reusable;
    @JsonProperty("description")
    private String rocket_description;
    @JsonProperty("full_name")
    private String fullName;
    @JsonProperty("manufacturer")
    private Manufacturer manufacturer;
    @JsonProperty("variant")
    private String rocket_variant;
    @JsonProperty("alias")
    private String alias;
    @JsonProperty("min_stage")
    private Integer min_stage;
    @JsonProperty("max_stage")
    private Integer max_stage;
    @JsonProperty("length")
    private Double length;
    @JsonProperty("diameter")
    private Double diameter;
    @JsonProperty("maiden_flight")
    private String maiden_flight;
    @JsonProperty("launch_cost")
    private Double launch_cost;
    @JsonProperty("launch_mass")
    private Double launch_mass;
    @JsonProperty("leo_capacity")
    private Integer leo_capacity;
    @JsonProperty("gto_capacity")
    private Integer gto_capacity;
    @JsonProperty("geo_capacity")
    private Integer geo_capacity;
    @JsonProperty("sso_capacity")
    private Integer sso_capacity;
    @JsonProperty("to_thrust")
    private Integer to_thrust;
    @JsonProperty("apogee")
    private Integer apogee;
    @JsonProperty("image")
    private RocketImages image;
    @JsonProperty("info_url")
    private String rocket_info_url;
    @JsonProperty("wiki_url")
    private String rocket_wiki_url;
    @JsonProperty("total_launch_count")
    private Integer rocket_total_launch_count;
    @JsonProperty("consecutive_successful_launches")
    private Integer rocket_consecutive_successful_launches;
    @JsonProperty("successful_launches")
    private Integer rocket_successful_launches;
    @JsonProperty("failed_launches")
    private Integer rocket_failed_launches;
    @JsonProperty("pending_launches")
    private Integer rocket_pending_launches;
    @JsonProperty("attempted_landings")
    private Integer rocket_attempted_landings;
    @JsonProperty("successful_landings")
    private Integer rocket_successful_landings;
    @JsonProperty("failed_landings")
    private Integer rocket_failed_landings;
    @JsonProperty("consecutive_successful_landings")
    private Integer rocket_consecutive_successful_landings;
}
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
class RocketImages {
    @JsonProperty("id")
    private Integer rocket_image_id;
    @JsonProperty("name")
    private String rocket_image_name;
    @JsonProperty("image_url")
    private String rocket_image_url;
    @JsonProperty("thumbnail_url")
    private String rocket_thumbnail_url;
    @JsonProperty("credit")
    private String rocket_credit;
}