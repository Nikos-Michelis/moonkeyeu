package com.moonkeyeu.core.api.launch.model.astronaut;

import com.moonkeyeu.core.api.launch.model.agency.Agencies;
import com.moonkeyeu.core.api.launch.model.country.Country;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@NamedEntityGraph(
        name = "astronaut-with-status-images-nationality-socialmedia",
        attributeNodes = {
                @NamedAttributeNode(value = "status", subgraph = "status"),
                @NamedAttributeNode(value = "astronautImages", subgraph = "astronautImages"),
                @NamedAttributeNode(value = "countries", subgraph = "countries"),
                @NamedAttributeNode(value = "socialMedia", subgraph = "socialMedia"),
                @NamedAttributeNode(value = "agency", subgraph = "agency"),
                @NamedAttributeNode(value = "crewMembers", subgraph = "crewMembers"),
        },
        subgraphs = {
                @NamedSubgraph(
                        name = "status",
                        attributeNodes = {
                                @NamedAttributeNode("statusName")
                        }
                ),
                @NamedSubgraph(
                        name = "astronautImages",
                        attributeNodes = {
                                @NamedAttributeNode("imageId"),
                                @NamedAttributeNode("credit"),
                                @NamedAttributeNode("imageName"),
                                @NamedAttributeNode("imageUrl"),
                                @NamedAttributeNode("thumbnailUrl")
                        }
                ),
                 @NamedSubgraph(
                        name = "agency",
                        attributeNodes = {
                                @NamedAttributeNode(value = "agenciesImages", subgraph = "agenciesImages"),
                                @NamedAttributeNode(value = "agencyType", subgraph = "agencyType"),
                        }
                 ),
                @NamedSubgraph(
                        name = "agenciesImages",
                        attributeNodes = {}
                ),
                @NamedSubgraph(
                        name = "agencyType",
                        attributeNodes = {}
                ),
                @NamedSubgraph(
                        name = "countries",
                        attributeNodes = {
                                @NamedAttributeNode("nationalityName"),
                                @NamedAttributeNode("alpha2Code"),
                                @NamedAttributeNode("alpha3Code")
                        }
                ),
                @NamedSubgraph(
                        name = "socialMedia",
                        attributeNodes = {
                                @NamedAttributeNode("mediaUrl"),
                                @NamedAttributeNode("name")
                        }
                ),
                @NamedSubgraph(
                        name = "crewMembers",
                        attributeNodes = {}
                )
        }
)

@Table(name = "astronaut", schema = "moonkey_db")
public class Astronaut {
    @Id
    @Column(name = "astronaut_id")
    private Long astronautId;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "in_space")
    private Boolean inSpace;
    @Basic
    @Column(name = "date_of_death")
    private LocalDate dateOfDeath;
    @Basic
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;
    @Basic
    @Column(name = "age")
    private Integer age;
    @Basic
    @Column(name = "bio")
    private String bio;
    @Basic
    @Column(name = "wiki_url")
    private String wikiUrl;
    @Basic
    @Column(name = "last_flight")
    private Instant lastFlight;
    @Basic
    @Column(name = "first_flight")
    private Instant firstFlight;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id")
    private AstronautStatus status;
    @OneToMany(mappedBy = "astronaut")
    @BatchSize(size = 10)
    private Set<AstronautImages> astronautImages;
    @OneToMany(mappedBy = "astronaut")
    @BatchSize(size = 10)
    private Set<SocialMedia> socialMedia;
    @OneToMany(mappedBy = "astronaut")
    @BatchSize(size = 10)
    private Set<CrewMember> crewMembers;
    @ManyToMany
    @JoinTable(
            name = "astronaut_has_country",
            joinColumns = @JoinColumn(name = "astronaut_id"),
            inverseJoinColumns = @JoinColumn(name = "country_id")
    )
    @BatchSize(size = 10)
    private Set<Country> countries;

    @ManyToOne
    @JoinColumn(name = "agency_id")
    private Agencies agency;
    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Astronaut astronaut = (Astronaut) object;
        return Objects.equals(astronautId, astronaut.astronautId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(astronautId);
    }
}
