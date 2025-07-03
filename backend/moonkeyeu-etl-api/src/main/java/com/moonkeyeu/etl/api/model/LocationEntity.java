package com.moonkeyeu.etl.api.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Entity
@Table(name = "location", schema = "moonkey_db")
public class LocationEntity {
    @Id
    @Column(name = "location_id")
    private Long location_id;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "description")
    private String description;
    @Basic
    @Column(name = "map_image")
    private String map_image;
    @Basic
    @Column(name = "location_timezone")
    private String location_timezone;
    @Basic
    @Column(name = "total_launch_count")
    private Integer total_launch_count;
    @Basic
    @Column(name = "total_landing_count")
    private Integer total_landing_count;
}
