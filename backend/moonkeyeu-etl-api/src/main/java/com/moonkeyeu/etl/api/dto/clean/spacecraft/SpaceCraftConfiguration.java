package com.moonkeyeu.etl.api.dto.clean.spacecraft;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.moonkeyeu.etl.api.dto.clean.CsvEntity;
import lombok.Data;

@Data
@JsonPropertyOrder({
        "spacecraft_conf_id",
        "name",
        "type_id",
        "in_use",
        "capability",
        "history",
        "details",
        "maiden_flight",
        "height",
        "diameter",
        "human_rated",
        "crew_capacity",
        "payload_capacity",
        "payload_return_capacity",
        "flight_life",
        "wiki_link",
        "info_link",
        "agency_id"
})
@JsonIgnoreProperties(ignoreUnknown = true)
public class SpaceCraftConfiguration implements CsvEntity {
    @JsonProperty("spacecraft_conf_id")
    private String spacecraft_conf_id;
    @JsonProperty("spacecraft_conf_name")
    private String name;
    @JsonProperty("spacecraft_type_id")
    private Integer type_id;
    @JsonProperty("in_use")
    private String in_use;
    @JsonProperty("capability")
    private String capability;
    @JsonProperty("history")
    private String history;
    @JsonProperty("details")
    private String details;
    @JsonProperty("maiden_flight")
    private String maiden_flight;
    @JsonProperty("height")
    private Double height;
    @JsonProperty("diameter")
    private Double diameter;
    @JsonProperty("human_rated")
    private String human_rated;
    @JsonProperty("crew_capacity")
    private Integer crew_capacity;
    @JsonProperty("payload_capacity")
    private Integer payload_capacity;
    @JsonProperty("payload_return_capacity")
    private Integer payload_return_capacity;
    @JsonProperty("flight_life")
    private String flight_life;
    @JsonProperty("wiki_link")
    private String wiki_link;
    @JsonProperty("info_link")
    private String info_link;
    @JsonProperty("agency_id")
    private Integer agency_id;

    @Override
    public String getPrimaryKey() {
        return spacecraft_conf_id;
    }
}
