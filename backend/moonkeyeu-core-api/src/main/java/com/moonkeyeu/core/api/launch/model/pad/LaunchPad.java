package com.moonkeyeu.core.api.launch.model.pad;

import com.moonkeyeu.core.api.launch.model.agency.Agencies;
import com.moonkeyeu.core.api.launch.model.launch.Launch;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@NamedEntityGraph(
        name = "launch-pad-with-agencies-location-launches-images",
        attributeNodes = {
                @NamedAttributeNode(value = "agencies", subgraph = "agencies"),
                @NamedAttributeNode(value = "location", subgraph = "location"),
                @NamedAttributeNode(value = "launches", subgraph = "launches"),
                @NamedAttributeNode(value = "launchPadImages", subgraph = "launchPadImages")
        },
        subgraphs = {
                @NamedSubgraph(
                        name = "agencies",
                        attributeNodes = {}
                ),
                @NamedSubgraph(
                        name = "location",
                        attributeNodes = {}
                ),
                @NamedSubgraph(
                        name = "launches",
                        attributeNodes = {}
                ),
                @NamedSubgraph(
                        name = "launchPadImages",
                        attributeNodes = {}
                ),
        }
)
@Table(name = "launch_pad", schema = "moonkey_db")
public class LaunchPad {
    @Id
    @Column(name = "launch_pad_id")
    private Long launchPadId;
    @Basic
    @Column(name = "name")
    private String launchPadName;
    @Basic
    @Column(name = "active")
    private boolean active;
    @Basic
    @Column(name = "description")
    private String description;
    @Basic
    @Column(name = "info_url")
    private String infoUrl;
    @Basic
    @Column(name = "wiki_url")
    private String wikiUrl;
    @Basic
    @Column(name = "map_url")
    private String mapUrl;
    @Basic
    @Column(name = "latitude")
    private BigDecimal latitude;
    @Basic
    @Column(name = "longitude")
    private BigDecimal longitude;
    @Basic
    @Column(name = "map_image")
    private String mapImage;
    @Basic
    @Column(name = "total_launch_count")
    private Integer totalLaunchCount;
    @Basic
    @Column(name = "orbital_launch_attempt_count")
    private Integer orbitalLaunchAttemptCount;
    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;
    @OneToMany(mappedBy = "launchPad", fetch = FetchType.LAZY)
    @BatchSize(size = 10)
    private Set<Launch> launches;
    @OneToMany(mappedBy = "launchPad", fetch = FetchType.LAZY)
    @BatchSize(size = 10)
    private Set<LaunchPadImages> launchPadImages;
    @ManyToMany
    @JoinTable(
            name = "pad_has_agencies",
            joinColumns = @JoinColumn(name = "launch_pad_id"),
            inverseJoinColumns = @JoinColumn(name = "agency_id")
    )
    @BatchSize(size = 20)
    private Set<Agencies> agencies;
    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        LaunchPad launchPad = (LaunchPad) object;
        return Objects.equals(launchPadId, launchPad.launchPadId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(launchPadId);
    }
}
