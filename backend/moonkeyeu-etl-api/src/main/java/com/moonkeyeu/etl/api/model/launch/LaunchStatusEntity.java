package com.moonkeyeu.etl.api.model.launch;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Entity
@Table(name = "launch_status", schema = "moonkey_db")
public class LaunchStatusEntity  {
    @Id
    @Column(name = "status_id", nullable = false)
    private Long status_id;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "abbrev")
    private String abbrev;
    @Basic
    @Column(name = "description")
    private String description;
}
