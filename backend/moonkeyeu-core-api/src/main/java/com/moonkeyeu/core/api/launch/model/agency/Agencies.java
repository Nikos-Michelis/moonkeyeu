package com.moonkeyeu.core.api.launch.model.agency;

import com.moonkeyeu.core.api.launch.model.pad.LaunchPad;
import com.moonkeyeu.core.api.launch.model.astronaut.Astronaut;
import com.moonkeyeu.core.api.launch.model.country.Country;
import com.moonkeyeu.core.api.launch.model.launch.Launch;
import com.moonkeyeu.core.api.launch.model.mission.Mission;
import com.moonkeyeu.core.api.launch.model.program.Programs;
import com.moonkeyeu.core.api.launch.model.rocket.RocketConfiguration;
import com.moonkeyeu.core.api.launch.model.spacecraft.SpacecraftConfiguration;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;

import java.util.Set;

@NamedEntityGraph(
        name = "agencies-configurations-images",
        attributeNodes = {
                @NamedAttributeNode(value = "rocketConfigurations", subgraph = "rocketConfigurations"),
                @NamedAttributeNode(value = "spacecraftConfigurations", subgraph = "spacecraftConfigurations"),
                @NamedAttributeNode(value = "agencyType", subgraph = "agencyType"),
                @NamedAttributeNode(value = "agenciesImages", subgraph = "agenciesImages")

        },
        subgraphs = {
                @NamedSubgraph(
                        name = "rocketConfigurations",
                        attributeNodes = {}
                ),
                @NamedSubgraph(
                        name = "spacecraftConfigurations",
                        attributeNodes = {}
                ),
                @NamedSubgraph(
                        name = "agencyType",
                        attributeNodes = {}
                ),
                @NamedSubgraph(
                        name = "agenciesImages",
                        attributeNodes = {}
                ),

        }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "agencies", schema = "moonkey_db")
@EqualsAndHashCode(of = "agencyId")
public class Agencies {
    @Id
    @Column(name = "agency_id")
    private Long agencyId;
    @Basic
    @Column(name = "name")
    private String agencyName;
    @Basic
    @Column(name = "featured")
    private String featured;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id")
    private AgencyType agencyType;
    @Basic
    @Column(name = "abbrev")
    private String abbrev;
    @Basic
    @Column(name = "description")
    private String description;
    @Basic
    @Column(name = "administrator")
    private String administrator;
    @Basic
    @Column(name = "founding_year")
    private String foundingYear;
    @Basic
    @Column(name = "launchers")
    private String launchers;
    @Basic
    @Column(name = "spacecraft")
    private String spacecraft;
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
    @Column(name = "consecutive_successful_landings")
    private Integer consecutiveSuccessfulLandings;
    @Basic
    @Column(name = "successful_landings")
    private Integer successfulLandings;
    @Basic
    @Column(name = "failed_landings")
    private Integer failedLandings;
    @Basic
    @Column(name = "attempted_landings")
    private Integer attemptedLandings;
    @Basic
    @Column(name = "info_url")
    private String infoUrl;
    @Basic
    @Column(name = "wiki_url")
    private String wikiUrl;
    @OneToMany(mappedBy = "agencies")
    private Set<RocketConfiguration> rocketConfigurations;
    @OneToMany(mappedBy = "agencies")
    private Set<SpacecraftConfiguration> spacecraftConfigurations;
    @OneToMany(mappedBy = "agencies")
    @BatchSize(size = 50)
    private Set<Launch> launches;
    @ManyToMany(mappedBy = "agencies")
    private Set<Mission> missions;
    @OneToMany(mappedBy = "agencies")
    @BatchSize(size = 20)
    private Set<AgenciesImages> agenciesImages;
    @ManyToMany
    @JoinTable(
            name = "agencies_has_country",
            joinColumns = @JoinColumn(name = "agency_id"),
            inverseJoinColumns = @JoinColumn(name = "country_id")
    )
    @BatchSize(size = 20)
    private Set<Country> countries;
    @ManyToMany(mappedBy = "agencies")
    @BatchSize(size = 20)
    private Set<Programs> programs;
    @ManyToMany(mappedBy = "agencies")
    @BatchSize(size = 20)
    private Set<LaunchPad> launchPads;
    @OneToMany(mappedBy = "agency", fetch = FetchType.LAZY)
    @BatchSize(size = 20)
    private Set<Astronaut> astronauts;
}
