package com.moonkeyeu.core.api.launch.model.launcher;

import com.moonkeyeu.core.api.launch.model.landing.Landing;
import com.moonkeyeu.core.api.launch.model.rocket.Rocket;
import jakarta.persistence.*;
import lombok.*;

@Data
@Getter
@Setter
@Entity
@Table(name = "launcher_stage", schema = "moonkey_db")
public class LauncherStage {
    @Id
    @Column(name = "launcher_stage_id")
    private Long launcherStageId;
    @Basic
    @Column(name = "type")
    private String type;
    @Basic
    @Column(name = "reused")
    private Boolean reused;
    @Basic
    @Column(name = "launcher_flight_number")
    private Integer launcherFlightNumber;
    @ManyToOne
    @JoinColumn(name = "rocket_id")
    private Rocket rocket;
    @ManyToOne
    @JoinColumn(name = "launcher_id")
    private Launcher launcher;
    @ManyToOne
    @JoinColumn(name = "landing_id")
    private Landing landing;

}
