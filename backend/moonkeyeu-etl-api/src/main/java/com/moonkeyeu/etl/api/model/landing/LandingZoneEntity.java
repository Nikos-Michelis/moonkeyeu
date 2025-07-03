package com.moonkeyeu.etl.api.model.landing;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Entity
@Table(name = "landing_zone", schema = "moonkey_db")
public class LandingZoneEntity  {
    @Id
    @Column(name = "landing_zone_id", nullable = false)
    private Long landing_zone_id;
    @Basic
    @Column(name = "name", nullable = false)
    private String name;
    @Basic
    @Column(name = "abbrev", nullable = true)
    private String abbrev;
    @Basic
    @Column(name = "description", nullable = true)
    private String description;
    @Basic
    @Column(name = "successful_landings", nullable = true)
    private Integer successful_landings;
    @Basic
    @Column(name = "location_id", nullable = true)
    private Integer location_id;
}
