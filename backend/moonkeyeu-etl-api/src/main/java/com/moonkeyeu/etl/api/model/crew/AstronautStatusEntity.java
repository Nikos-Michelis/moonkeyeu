package com.moonkeyeu.etl.api.model.crew;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Entity
@Table(name = "astronaut_status", schema = "moonkey_db")
public class AstronautStatusEntity  {
    @Id
    @Column(name = "status_id", nullable = false)
    private Long status_id;
    @Basic
    @Column(name = "name", nullable = false, length = 255)
    private String name;
}
