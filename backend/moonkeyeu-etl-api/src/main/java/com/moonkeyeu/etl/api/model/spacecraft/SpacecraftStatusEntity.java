package com.moonkeyeu.etl.api.model.spacecraft;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Entity
@Table(name = "spacecraft_status", schema = "moonkey_db")
public class SpacecraftStatusEntity  {
    @Id
    @Column(name = "status_id")
    private Long status_id;
    @Basic
    @Column(name = "name")
    private String name;
}
