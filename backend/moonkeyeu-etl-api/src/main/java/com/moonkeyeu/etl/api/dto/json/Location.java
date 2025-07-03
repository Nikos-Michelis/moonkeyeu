package com.moonkeyeu.etl.api.dto.json;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.moonkeyeu.etl.api.dto.json.country.Country;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Location implements Serializable {
    @JsonProperty("id")
    private Integer location_id;
    @JsonProperty("name")
    private String location_name;
    private Country country;
    @JsonProperty("description")
    private String location_description;
    @JsonProperty("map_image")
    private String location_map_image;
    @JsonProperty("timezone_name")
    private String location_timezone;
    @JsonProperty("total_launch_count")
    private Integer total_location_launches;
    @JsonProperty("total_landing_count")
    private Integer total_location_landings;
}
