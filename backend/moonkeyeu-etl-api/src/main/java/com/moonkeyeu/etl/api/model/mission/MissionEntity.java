package com.moonkeyeu.etl.api.model.mission;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Entity
@Table(name = "mission", schema = "moonkey_db")
public class MissionEntity  {
    @Id
    @Column(name = "mission_id", nullable = false)
    private Long mission_id;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "description")
    private String description;
    @Basic
    @Column(name = "type")
    private String type;
    @Basic
    @Column(name = "orbit_id")
    private Integer orbit_id;
}
