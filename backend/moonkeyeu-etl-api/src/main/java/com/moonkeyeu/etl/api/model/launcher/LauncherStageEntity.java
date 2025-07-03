package com.moonkeyeu.etl.api.model.launcher;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Entity
@Table(name = "launcher_stage", schema = "moonkey_db")
public class LauncherStageEntity  {
    @Id
    @Column(name = "launcher_stage_id")
    private Long launcher_stage_id;
    @Basic
    @Column(name = "type")
    private String type;
    @Basic
    @Column(name = "reused", columnDefinition = "TINYINT(1)")
    private Boolean reused;
    @Basic
    @Column(name = "launcher_flight_number")
    private String launcher_flight_number;
    @Basic
    @Column(name = "rocket_id")
    private Integer rocket_id;
    @Basic
    @Column(name = "launcher_id")
    private Integer launcher_id;
    @Basic
    @Column(name = "landing_id")
    private Integer landing_id;
}
