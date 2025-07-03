package com.moonkeyeu.etl.api.dto.json.spacecraft;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.moonkeyeu.etl.api.dto.json.Status;
import com.moonkeyeu.etl.api.dto.json.agency.Agency;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Spacecraft {
    @JsonProperty("id")
    private Integer spacecraft_id;
    @JsonProperty("name")
    private String spacecraft_name;
    private String serial_number;
    private boolean is_placeholder;
    private boolean in_space;
    private Integer flights_count;
    private Integer mission_ends_count;
    @JsonProperty("status")
    private Status status;
    @JsonProperty("description")
    private String spacecraft_description;
    @JsonProperty("spacecraft_config")
    private SpaceCraftConfiguration spacecraft_config;
}
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
class SpaceCraftConfiguration {
    @JsonProperty("id")
    private Integer spacecraft_conf_id;
    @JsonProperty("name")
    private String spacecraft_conf_name;
    @JsonProperty("type")
    private SpaceCraftType space_craft_type;
    @JsonProperty("agency")
    private Agency agency;
    private boolean in_use;
    @JsonProperty("image")
    private SpaceCraftImages image;
    private String capability;
    private String history;
    private String details;
    private String maiden_flight;
    private Double height;
    private Double diameter;
    private Boolean human_rated;
    private Integer crew_capacity;
    private Integer payload_capacity;
    private Integer payload_return_capacity;
    private String flight_life;
    private String wiki_link;
    private String info_link;
    private Integer spacecraft_flown;
    private Integer total_launch_count;
    private Integer successful_launches;
    private Integer failed_launches;
    private Integer attempted_landings;
    private Integer successful_landings;
    private Integer failed_landings;
}
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
class SpaceCraftType{
    @JsonProperty("id")
    private Integer spacecraft_type_id;
    @JsonProperty("name")
    private String spacecraft_type;
}
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
class SpaceCraftImages {
    @JsonProperty("id")
    private Integer spacecraft_image_id;
    @JsonProperty("name")
    private String spacecraft_image_name;
    @JsonProperty("image_url")
    private String spacecraft_image_url;
    @JsonProperty("thumbnail_url")
    private String spacecraft_thumbnail_url;
    @JsonProperty("credit")
    private String spacecraft_credit;
}
