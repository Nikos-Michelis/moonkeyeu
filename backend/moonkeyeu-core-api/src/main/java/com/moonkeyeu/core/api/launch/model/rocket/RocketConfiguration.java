package com.moonkeyeu.core.api.launch.model.rocket;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.moonkeyeu.core.api.launch.model.agency.Agencies;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@NamedEntityGraph(
        name = "rocket-configuration-images-agency",
        attributeNodes = {
                @NamedAttributeNode(value = "agencies", subgraph = "agencies"),
                @NamedAttributeNode(value = "rocketConfImages", subgraph = "rocketConfImages"),
                @NamedAttributeNode(value = "rockets", subgraph = "rockets"),
        },
        subgraphs = {
                @NamedSubgraph(
                        name = "agencies",
                        attributeNodes = {
                                @NamedAttributeNode("agencyType")
                        }
                ),
                @NamedSubgraph(
                        name = "rocketConfImages",
                        attributeNodes = {}
                ),
                @NamedSubgraph(
                        name = "rockets",
                        attributeNodes = {}
                )
        }
)
@Table(name = "rocket_configuration", schema = "moonkey_db")
public class RocketConfiguration {
    @Id
    @Column(name = "rocket_conf_id")
    private Long rocketConfId;
    @Basic
    @Column(name = "name")
    private String rocketName;
    @Basic
    @Column(name = "variant")
    private String variant;
    @Basic
    @Column(name = "fullname")
    private String fullname;
    @Basic
    @Column(name = "active")
    private Boolean active;
    @Basic
    @Column(name = "reusable")
    private Boolean reusable;
    @Basic
    @Column(name = "description")
    private String description;
    @Basic
    @Column(name = "alias")
    private String alias;
    @Basic
    @Column(name = "min_stage")
    private Integer minStage;
    @Basic
    @Column(name = "max_stage")
    private Integer maxStage;
    @Basic
    @Column(name = "maiden_flight")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate maidenFlight;
    @Basic
    @Column(name = "length")
    private Double length;
    @Basic
    @Column(name = "diameter")
    private Double diameter;
    @Basic
    @Column(name = "launch_cost")
    private Double launchCost;
    @Basic
    @Column(name = "launch_mass")
    private Double launchMass;
    @Basic
    @Column(name = "leo_capacity")
    private Double leoCapacity;
    @Basic
    @Column(name = "gto_capacity")
    private Double gtoCapacity;
    @Basic
    @Column(name = "geo_capacity")
    private Double geoCapacity;
    @Basic
    @Column(name = "sso_capacity")
    private Double ssoCapacity;
    @Basic
    @Column(name = "to_thrust")
    private Integer toThrust;
    @Basic
    @Column(name = "apogee")
    private Integer apogee;
    @Basic
    @Column(name = "info_url")
    private String infoUrl;
    @Basic
    @Column(name = "wiki_url")
    private String wikiUrl;
    @Basic
    @Column(name = "total_launch_count")
    private Integer totalLaunchCount;
    @Basic
    @Column(name = "consecutive_successful_launches")
    private Integer consecutiveSuccessfulLaunches;
    @Basic
    @Column(name = "successful_launches")
    private Integer successfulLaunches;
    @Basic
    @Column(name = "failed_launches")
    private Integer failedLaunches;
    @Basic
    @Column(name = "pending_launches")
    private Integer pendingLaunches;
    @Basic
    @Column(name = "attempted_landings")
    private Integer attemptedLandings;
    @Basic
    @Column(name = "successful_landings")
    private Integer successfulLandings;
    @Basic
    @Column(name = "failed_landings")
    private Integer failedLandings;
    @Basic
    @Column(name = "consecutive_successful_landings")
    private Integer consecutiveSuccessfulLandings;
    @ManyToOne
    @JoinColumn(name = "agency_id")
    private Agencies agencies;

    @OneToMany(mappedBy = "rocketConfiguration")
    @BatchSize(size = 20)
    private Set<Rocket> rockets;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_id")
    private RocketConfImages rocketConfImages;

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        RocketConfiguration that = (RocketConfiguration) object;
        return Objects.equals(rocketConfId, that.rocketConfId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rocketConfId);
    }
}
