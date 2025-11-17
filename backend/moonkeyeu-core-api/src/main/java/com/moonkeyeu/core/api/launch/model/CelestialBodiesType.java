package com.moonkeyeu.core.api.launch.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "celestial_bodies_types")
public class CelestialBodiesType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "type_id", nullable = false)
    private Long typeId;
    @Column(name = "name", nullable = false)
    private String typeName;
}