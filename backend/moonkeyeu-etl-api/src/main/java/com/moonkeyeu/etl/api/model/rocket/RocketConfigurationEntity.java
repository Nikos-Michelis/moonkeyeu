package com.moonkeyeu.etl.api.model.rocket;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Data
@Getter
@Setter
@Entity
@Table(name = "rocket_configuration", schema = "moonkey_db")
@JsonIgnoreProperties(ignoreUnknown = true)
public class RocketConfigurationEntity  {
    @Id
    @Column(name = "rocket_conf_id")
    private Long rocket_conf_id;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "variant")
    private String variant;
    @Basic
    @Column(name = "fullname")
    private String fullname;
    @Basic
    @Column(name = "active", columnDefinition = "TINYINT(1)")
    private Boolean active;
    @Basic
    @Column(name = "reusable", columnDefinition = "TINYINT(1)")
    private Boolean reusable;
    @Basic
    @Column(name = "description")
    private String description;
    @Basic
    @Column(name = "alias")
    private String alias;
    @Basic
    @Column(name = "min_stage")
    private Integer min_stage;
    @Basic
    @Column(name = "max_stage")
    private Integer max_stage;
    @Basic
    @Column(name = "maiden_flight")
    private Date maiden_flight;
    @Basic
    @Column(name = "length")
    private Double length;
    @Basic
    @Column(name = "diameter")
    private Double diameter;
    @Basic
    @Column(name = "launch_cost")
    private Double launch_cost;
    @Basic
    @Column(name = "launch_mass")
    private Double launch_mass;
    @Basic
    @Column(name = "leo_capacity")
    private Double leo_capacity;
    @Basic
    @Column(name = "gto_capacity")
    private Double gto_capacity;
    @Basic
    @Column(name = "geo_capacity")
    private Double geo_capacity;
    @Basic
    @Column(name = "sso_capacity")
    private Double sso_capacity;
    @Basic
    @Column(name = "to_thrust")
    private Integer to_thrust;
    @Basic
    @Column(name = "apogee")
    private Integer apogee;
    @Basic
    @Column(name = "info_url")
    private String info_url;
    @Basic
    @Column(name = "wiki_url")
    private String wiki_url;
    @Basic
    @Column(name = "total_launch_count")
    private Integer total_launch_count;
    @Basic
    @Column(name = "consecutive_successful_launches")
    private Integer consecutive_successful_launches;
    @Basic
    @Column(name = "successful_launches")
    private Integer successful_launches;
    @Basic
    @Column(name = "failed_launches")
    private Integer failed_launches;
    @Basic
    @Column(name = "pending_launches")
    private Integer pending_launches;
    @Basic
    @Column(name = "attempted_landings")
    private Integer attempted_landings;
    @Basic
    @Column(name = "successful_landings")
    private Integer successful_landings;
    @Basic
    @Column(name = "failed_landings")
    private Integer failed_landings;
    @Basic
    @Column(name = "consecutive_successful_landings")
    private Integer consecutive_successful_landings;
    @Basic
    @Column(name = "agency_id")
    private Integer agency_id;
    @Basic
    @Column(name = "image_id")
    private Integer image_id;
}
