package com.moonkeyeu.core.api.launch.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "celestial_bodies")
public class CelestialBody {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cel_body_id", nullable = false)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "type_id", nullable = false)
    private CelestialBodiesType type;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "mass", precision = 38)
    private BigDecimal mass;
    @Column(name = "diameter", precision = 10, scale = 2)
    private BigDecimal diameter;
    @Column(name = "gravity", precision = 6, scale = 3)
    private BigDecimal gravity;
    @Column(name = "tilt", precision = 5, scale = 2)
    private BigDecimal tilt;
    @Column(name = "rotation", precision = 10, scale = 2)
    private BigDecimal rotation;
    @ColumnDefault("0")
    @Column(name = "orbit", nullable = false)
    private Integer orbit;
    @Column(name = "distance", precision = 15)
    private BigDecimal distance;
    @ColumnDefault("0")
    @Column(name = "moons", nullable = false)
    private Integer moons;
    @Column(name = "atmosphere")
    private Boolean atmosphere;
    @Column(name = "description", nullable = false)
    private String description;
    @Column(name = "img_day", nullable = false)
    private String imgDay;
    @Column(name = "img_night")
    private String imgNight;
    @Column(name = "img_atmosphere")
    private String imgAtmosphere;
    @Column(name = "img_ring")
    private String imgRing;
}