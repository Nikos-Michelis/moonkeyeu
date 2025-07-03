package com.moonkeyeu.etl.api.model.mission;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Entity
@Table(name = "orbit", schema = "moonkey_db")
public class OrbitEntity  {
    @Id
    @Column(name = "orbit_id")
    private Long orbit_id;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "abbrev")
    private String abbrev;
}
