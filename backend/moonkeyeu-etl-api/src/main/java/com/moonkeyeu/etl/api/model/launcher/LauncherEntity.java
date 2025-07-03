package com.moonkeyeu.etl.api.model.launcher;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Data
@Getter
@Setter
@Entity
@Table(name = "launcher", schema = "moonkey_db")
public class LauncherEntity  {
    @Id
    @Column(name = "launcher_id")
    private Long launcher_id;
    @Basic
    @Column(name = "details")
    private String details;
    @Basic
    @Column(name = "flight_proven", columnDefinition = "TINYINT(1)")
    private Boolean flight_proven;
    @Basic
    @Column(name = "serial_number")
    private String serial_number;
    @Basic
    @Column(name = "successful_landings")
    private Integer successful_landings;
    @Basic
    @Column(name = "attempted_landings")
    private Integer attempted_landings;
    @Basic
    @Column(name = "flights")
    private Integer flights;
    @Basic
    @Column(name = "last_launch_date", columnDefinition = "DATETIME")
    private Instant last_launch_date;
    @Basic
    @Column(name = "first_launch_date", columnDefinition = "DATETIME")
    private Instant first_launch_date;
    @Basic
    @Column(name = "status_id")
    private Integer status_id;
}
