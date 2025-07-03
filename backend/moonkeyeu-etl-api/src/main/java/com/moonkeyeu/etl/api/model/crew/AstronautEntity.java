package com.moonkeyeu.etl.api.model.crew;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDate;

@Data
@Getter
@Setter
@Entity
@Table(name = "astronaut", schema = "moonkey_db")
@JsonIgnoreProperties(ignoreUnknown = true)
public class AstronautEntity  {
    @Id
    @Column(name = "astronaut_id", nullable = false)
    private Long astronaut_id;
    @Basic
    @Column(name = "name", nullable = false, length = 45)
    private String name;
    @Basic
    @Column(name = "in_space", columnDefinition = "TINYINT(1)", nullable = true)
    private Boolean in_space;
    @Basic
    @Column(name = "date_of_death", nullable = true, length = 45)
    private LocalDate date_of_death;
    @Basic
    @Column(name = "date_of_birth", nullable = true, length = 45)
    private LocalDate date_of_birth;
    @Basic
    @Column(name = "age", nullable = true)
    private Integer age;
    @Basic
    @Column(name = "bio", nullable = true, length = -1)
    private String bio;
    @Basic
    @Column(name = "wiki_url", nullable = true, length = 255)
    private String wiki_url;
    @Basic
    @Column(name = "last_flight", columnDefinition = "DATETIME", nullable = true)
    private Instant last_flight;
    @Basic
    @Column(name = "first_flight", columnDefinition = "DATETIME", nullable = true)
    private Instant first_flight;
    @Basic
    @Column(name = "status_id")
    private Integer status_id;
    @Basic
    @Column(name = "agency_id")
    private Integer agency_id;
}
