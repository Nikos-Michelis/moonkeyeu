package com.moonkeyeu.etl.api.model.pad;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Data
@Getter
@Setter
@Entity
@Table(name = "launch_pad", schema = "moonkey_db")
public class LaunchPadEntity  {
    @Id
    @Column(name = "launch_pad_id")
    private Long launch_pad_id;
    @Basic
    @Column(name = "active", columnDefinition = "TINYINT(1)")
    private Boolean active;
    @Basic
    @Column(name = "name", nullable = true, length = 255)
    private String name;
    @Basic
    @Column(name = "description")
    private String description;
    @Basic
    @Column(name = "info_url", nullable = true, length = 255)
    private String info_url;
    @Basic
    @Column(name = "wiki_url", nullable = true, length = 255)
    private String wiki_url;
    @Basic
    @Column(name = "map_url", nullable = true, length = 255)
    private String map_url;
    @Basic
    @Column(name = "latitude")
    private BigDecimal latitude;
    @Basic
    @Column(name = "longitude")
    private BigDecimal longitude;
    @Basic
    @Column(name = "map_image", nullable = true, length = 255)
    private String map_image;
    @Basic
    @Column(name = "total_launch_count", nullable = true)
    private Integer total_launch_count;
    @Basic
    @Column(name = "orbital_launch_attempt_count", nullable = true)
    private Integer orbital_launch_attempt_count;
    @Basic
    @Column(name = "agency_id", nullable = true)
    private Integer agency_id;
    @Basic
    @Column(name = "location_id")
    private Integer location_id;
}
