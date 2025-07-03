package com.moonkeyeu.etl.api.model.landing;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Entity
@Table(name = "landing", schema = "moonkey_db")
public class LandingEntity  {
    @Id
    @Column(name = "landing_id", nullable = false)
    private Long landing_id;
    @Basic
    @Column(name = "attempt", columnDefinition = "TINYINT(1)", nullable = true)
    private Boolean attempt;
    @Basic
    @Column(name = "success", nullable = true, length = 45)
    private String success;
    @Basic
    @Column(name = "description", nullable = true, length = -1)
    private String description;
    @Basic
    @Column(name = "downrange_distance", nullable = true)
    private Integer downrange_distance;
    @Basic
    @Column(name = "landing_zone_id")
    private Integer landing_zone_id;
    @Basic
    @Column(name = "landing_type_id", nullable = true)
    private Integer landing_type_id;
}
