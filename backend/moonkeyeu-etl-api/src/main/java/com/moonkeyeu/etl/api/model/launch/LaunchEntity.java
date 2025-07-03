package com.moonkeyeu.etl.api.model.launch;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Data
@Getter
@Setter
@Entity
@Table(name = "launch", schema = "moonkey_db")
@JsonIgnoreProperties(ignoreUnknown = true)
public class LaunchEntity  {
    @Id
    @Column(name = "launch_id")
    private String launch_id;
    @Basic
    @Column(name = "slug")
    private String slug;
    @Basic
    @Column(name = "flightclub_url")
    private String flightclub_url;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "last_updated", columnDefinition = "DATETIME")
    private Instant last_updated;
    @Basic
    @Column(name = "net", columnDefinition = "DATETIME")
    private Instant net;
    @Basic
    @Column(name = "window_start", columnDefinition = "DATETIME")
    private Instant window_start;
    @Basic
    @Column(name = "window_end", columnDefinition = "DATETIME")
    private Instant window_end;
    @Basic
    @Column(name = "probability")
    private Double probability;
    @Basic
    @Column(name = "weather_concerns")
    private String weather_concerns;
    @Basic
    @Column(name = "agency_id")
    private Integer agency_id;
    @Basic
    @Column(name = "rocket_id")
    private Integer rocket_id;
    @Basic
    @Column(name = "mission_id")
    private Integer mission_id;
    @Basic
    @Column(name = "launch_pad_id")
    private Integer launch_pad_id;
    @Basic
    @Column(name = "status_id")
    private String status_id;
}
