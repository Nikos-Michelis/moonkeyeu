package com.moonkeyeu.etl.api.model.landing;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Entity
@Table(name = "landing_type", schema = "moonkey_db")
public class LandingTypeEntity  {
    @Id
    @Column(name = "landing_type_id", nullable = false)
    private Long landing_type_id;
    @Basic
    @Column(name = "name", nullable = false, length = 45)
    private String name;
    @Basic
    @Column(name = "abbrev", nullable = false, length = 45)
    private String abbrev;
    @Basic
    @Column(name = "description", nullable = false, length = -1)
    private String description;
}
