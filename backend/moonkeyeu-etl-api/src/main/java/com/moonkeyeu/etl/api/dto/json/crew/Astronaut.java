package com.moonkeyeu.etl.api.dto.json.crew;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.moonkeyeu.etl.api.dto.json.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Astronaut {
    @JsonProperty("id")
    private String astronaut_id;
    @JsonProperty("name")
    private String astronaut_name;
    private Boolean in_space;
    @JsonProperty("status")
    private Status status;
    @JsonProperty("agency")
    private AstronautAgency astronautAgency;
    private Integer age;
    private String date_of_birth;
    private String date_of_death;
    private String bio;
    @JsonProperty("image")
    private AstronautImages image;
    private String wiki;
    private String last_flight;
    private String first_flight;
}
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
class AstronautImages {
    @JsonProperty("id")
    private Integer astronaut_image_id;
    @JsonProperty("name")
    private String astronaut_image_name;
    @JsonProperty("image_url")
    private String astronaut_image_url;
    @JsonProperty("thumbnail_url")
    private String astronaut_thumbnail_url;
    @JsonProperty("credit")
    private String astronaut_credit;
}