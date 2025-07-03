package com.moonkeyeu.etl.api.dto.clean.rocket;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.moonkeyeu.etl.api.dto.clean.CsvEntity;
import lombok.Data;

@Data
@JsonPropertyOrder({
        "rocket_id",
        "name",
        "active",
        "reusable",
        "description",
        "full_name",
        "variant",
        "alias",
        "min_stage",
        "max_stage",
        "length",
        "diameter",
        "maiden_flight",
        "launch_cost",
        "launch_mass",
        "leo_capacity",
        "gto_capacity",
        "geo_capacity",
        "sso_capacity",
        "to_thrust",
        "apogee",
        "info_url",
        "wiki_url",
        "total_launch_count",
        "consecutive_successful_launches",
        "successful_launches",
        "failed_launches",
        "pending_launches",
        "attempted_landings",
        "successful_landings",
        "failed_landings",
        "consecutive_successful_landings",
        "agency_id"
})
@JsonIgnoreProperties(ignoreUnknown = true)
public class RocketConfiguration implements CsvEntity {
    @JsonProperty( "rocket_conf_id")
    private String rocket_conf_id;
    @JsonProperty("rocket_name")
    private String name;
    @JsonProperty("rocket_variant")
    private String variant;
    @JsonProperty("fullName")
    private String fullname;
    @JsonProperty("active")
    private String active;
    @JsonProperty("reusable")
    private String reusable;
    @JsonProperty("rocket_description")
    private String description;
    @JsonProperty("alias")
    private String alias;
    @JsonProperty("min_stage")
    private Integer min_stage;
    @JsonProperty("max_stage")
    private Integer max_stage;
    @JsonProperty("maiden_flight")
    private String maiden_flight;
    @JsonProperty("length")
    private String length;
    @JsonProperty("diameter")
    private String diameter;
    @JsonProperty( "launch_cost")
    private Double launch_cost;
    @JsonProperty( "launch_mass")
    private Double launch_mass;
    @JsonProperty("leo_capacity")
    private String leo_capacity;
    @JsonProperty("gto_capacity")
    private String gto_capacity;
    @JsonProperty("geo_capacity")
    private Integer geo_capacity;
    @JsonProperty("sso_capacity")
    private Integer sso_capacity;
    @JsonProperty("to_thrust")
    private Integer to_thrust;
    @JsonProperty("apogee")
    private Integer apogee;
    @JsonProperty("rocket_info_url")
    private String info_url;
    @JsonProperty("rocket_wiki_url")
    private String wiki_url;
    @JsonProperty("rocket_total_launch_count")
    private Integer total_launch_count;
    @JsonProperty("rocket_consecutive_successful_launches")
    private Integer consecutive_successful_launches;
    @JsonProperty("rocket_successful_launches")
    private Integer successful_launches;
    @JsonProperty("rocket_failed_launches")
    private Integer failed_launches;
    @JsonProperty("rocket_pending_launches")
    private Integer pending_launches;
    @JsonProperty("rocket_attempted_landings")
    private Integer attempted_landings;
    @JsonProperty("rocket_successful_landings")
    private Integer successful_landings;
    @JsonProperty("rocket_failed_landings")
    private Integer failed_landings;
    @JsonProperty("rocket_consecutive_successful_landings")
    private Integer consecutive_successful_landings;
    @JsonProperty("manufacturer_id")
    private Integer agency_id;
    @JsonProperty("rocket_image_id")
    private Integer image_id;

    @Override
    public String getPrimaryKey() {
        return rocket_conf_id;
    }
}
