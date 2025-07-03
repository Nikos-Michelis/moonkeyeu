package com.moonkeyeu.etl.api.dto.clean.astronauts;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.moonkeyeu.etl.api.dto.clean.CsvEntity;
import lombok.Data;

import java.time.LocalDate;

@Data
@JsonPropertyOrder({"astronaut_id", "astronaut_name", "in_space", "status_id", "age", "date_of_birth", "date_of_death", "bio", "profile_image", "wiki", "last_flight", "first_flight"})
@JsonIgnoreProperties(ignoreUnknown = true)
public class Astronaut implements CsvEntity {
    @JsonProperty("astronaut_id")
    private String astronaut_id;
    @JsonProperty("astronaut_name")
    private String name;
    @JsonProperty("in_space")
    private String in_space;
    @JsonProperty("age")
    private Integer age;
    @JsonProperty("date_of_birth")
    private LocalDate date_of_birth;
    @JsonProperty("date_of_death")
    private LocalDate date_of_death;
    @JsonProperty("bio")
    private String bio;
    @JsonProperty("wiki")
    private String wiki_url;
    @JsonProperty("last_flight")
    private String last_flight;
    @JsonProperty("first_flight")
    private String first_flight;
    @JsonProperty("agency_id")
    private Integer agency_id;
    @JsonProperty("status_id")
    private Integer status_id;
    @JsonProperty("launch_id")
    private String launch_id;
    @Override
    public String getPrimaryKey() {
        return astronaut_id;
    }
}
