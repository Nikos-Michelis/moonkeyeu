package com.moonkeyeu.core.api.launch.dto.astronaut;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.moonkeyeu.core.api.launch.dto.agency.AgencyNormalDTO;
import com.moonkeyeu.core.api.launch.dto.info.SocialMediaDTO;
import com.moonkeyeu.core.api.launch.dto.CountryDTO;
import com.moonkeyeu.core.api.launch.dto.DTOEntity;
import com.moonkeyeu.core.api.launch.dto.ImageDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({"id", "name", "in_space", "date_of_death", "date_of_birth",
        "age", "bio", "wiki_url", "last_flight", "first_flight", "status",
        "agency", "images" ,"social_media", "nationality", "crew"})
public class AstronautDetailedDTO implements DTOEntity {
    @JsonProperty("id")
    private Long astronautId;
    @JsonProperty("name")
    private String name;
    @JsonProperty("in_space")
    private Boolean inSpace;
    @JsonProperty("date_of_death")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfDeath;
    @JsonProperty("date_of_birth")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;
    @JsonProperty("age")
    private Integer age;
    @JsonProperty("bio")
    private String bio;
    @JsonProperty("wiki_url")
    private String wikiUrl;
    @JsonProperty("last_flight")
    private Instant lastFlight;
    @JsonProperty("first_flight")
    private Instant firstFlight;
    @JsonProperty("status")
    private String statusName;
    @JsonProperty("agency")
    private AgencyNormalDTO agency;
    @JsonProperty("images")
    private Set<ImageDTO> astronautImages;
    @JsonProperty("social_media")
    private Set<SocialMediaDTO> socialMedia;
    @JsonProperty("nationality")
    private Set<CountryDTO> countries;
    @JsonProperty("crew")
    private Set<CrewMemberDetailedDTO> crewMembers;
}