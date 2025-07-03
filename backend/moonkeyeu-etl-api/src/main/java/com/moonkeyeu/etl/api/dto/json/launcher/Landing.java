package com.moonkeyeu.etl.api.dto.json.launcher;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Landing {
    @JsonProperty("id")
    private Integer landing_id;
    @JsonProperty("attempt")
    private String attempt;
    @JsonProperty("success")
    private String success;
    @JsonProperty("description")
    private String landing_description;
    private Integer downrange_distance;
    @JsonProperty("landing_location")
    private LandingZone landing_location;
    @JsonProperty("type")
    private LandingType landing_type;
}
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
class LandingType {
    @JsonProperty("id")
    private Integer type_id;
    @JsonProperty("name")
    private String type_name;
    @JsonProperty("abbrev")
    private String type_abbrev;
    @JsonProperty("description")
    private String type_description;
}
