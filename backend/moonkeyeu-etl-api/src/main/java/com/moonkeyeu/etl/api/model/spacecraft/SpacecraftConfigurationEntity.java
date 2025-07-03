package com.moonkeyeu.etl.api.model.spacecraft;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Data
@Getter
@Setter
@Entity
@Table(name = "spacecraft_configuration", schema = "moonkey_db")
@JsonIgnoreProperties(ignoreUnknown = true)
public class SpacecraftConfigurationEntity  {
    @Id
    @Column(name = "spacecraft_conf_id")
    private Long spacecraft_conf_id;
    @Basic
    @Column(name = "spacecraft_conf_name")
    @JsonProperty("name")
    private String spacecraft_conf_name;
    @Column(name = "type_id")
    private Integer type_id;
    @Basic
    @Column(name = "in_use", columnDefinition = "TINYINT(1)")
    private Boolean in_use;
    @Basic
    @Column(name = "capability")
    private String capability;
    @Basic
    @Column(name = "history")
    private String history;
    @Basic
    @Column(name = "details")
    private String details;
    @Basic
    @Column(name = "maiden_flight")
    private Date maiden_flight;
    @Basic
    @Column(name = "height")
    private Double height;
    @Basic
    @Column(name = "diameter")
    private Double diameter;
    @Basic
    @Column(name = "human_rated", columnDefinition = "TINYINT(1)")
    private Boolean human_rated;
    @Basic
    @Column(name = "crew_capacity")
    private Integer crew_capacity;
    @Basic
    @Column(name = "payload_capacity")
    private Integer payload_capacity;
    @Basic
    @Column(name = "payload_return_capacity")
    private Integer payload_return_capacity;
    @Basic
    @Column(name = "flight_life")
    private String flight_life;
    @Basic
    @Column(name = "wiki_link")
    private String wiki_link;
    @Basic
    @Column(name = "info_link")
    private String info_link;
    @Column(name = "agency_id")
    private Integer agency_id;
}
