package com.moonkeyeu.core.api.launch.model.launcher;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;

import java.time.Instant;
import java.util.Objects;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@NamedEntityGraph(
        name = "launcher-launcherStage-status-images",
        attributeNodes = {
                @NamedAttributeNode(value = "launcherStages", subgraph = "launcherStages"),
                @NamedAttributeNode(value = "status", subgraph = "status"),
                @NamedAttributeNode(value = "launcherImages", subgraph = "launcherImages"),
        },
        subgraphs = {
                @NamedSubgraph(
                        name = "launcherStages",
                        attributeNodes = {
                                @NamedAttributeNode("rocket"),
                                @NamedAttributeNode("launcher"),
                                @NamedAttributeNode("landing"),
                        }
                ),
                @NamedSubgraph(
                        name = "rocket",
                        attributeNodes = {}
                ),
                  @NamedSubgraph(
                        name = "launcher",
                        attributeNodes = {}
                ),
                  @NamedSubgraph(
                        name = "landing",
                        attributeNodes = {}
                ),

                 @NamedSubgraph(
                        name = "status",
                        attributeNodes = {}
                ),
                 @NamedSubgraph(
                        name = "launcherImages",
                        attributeNodes = {}
                ),

        }
)
@Table(name = "launcher", schema = "moonkey_db")
public class Launcher {
    @Id
    @Column(name = "launcher_id")
    private Long launcherId;
    @Basic
    @Column(name = "details")
    private String details;
    @Basic
    @Column(name = "flight_proven")
    private Byte flightProven;
    @Basic
    @Column(name = "serial_number")
    private String serialNumber;
    @Basic
    @Column(name = "successful_landings")
    private Integer successfulLandings;
    @Basic
    @Column(name = "attempted_landings")
    private Integer attemptedLandings;
    @Basic
    @Column(name = "flights")
    private Integer flights;
    @Basic
    @Column(name = "last_launch_date")
    private Instant lastLaunchDate;
    @Basic
    @Column(name = "first_launch_date")
    private Instant firstLaunchDate;
    @OneToMany(mappedBy = "launcher", fetch = FetchType.LAZY)
    @BatchSize(size = 20)
    private Set<LauncherStage> launcherStages;
    @ManyToOne
    @JoinColumn(name = "status_id")
    private LauncherStatus status;
    @OneToMany(mappedBy = "launcher", fetch = FetchType.LAZY)
    @BatchSize(size = 20)
    private Set<LauncherImages> launcherImages;

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Launcher launcher = (Launcher) object;
        return Objects.equals(launcherId, launcher.launcherId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(launcherId);
    }
}
