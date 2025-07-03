package com.moonkeyeu.core.api.launch.model.spacecraft;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.moonkeyeu.core.api.launch.model.agency.Agencies;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;


@NamedEntityGraph(
        name = "spacecraft-configuration-images",
        attributeNodes = {
                @NamedAttributeNode(value = "spacecraftType", subgraph = "spacecraftType"),
                @NamedAttributeNode(value = "spacecraftConfImages", subgraph = "spacecraftConfImages")

        },
        subgraphs = {
                @NamedSubgraph(
                        name = "spacecraftType",
                        attributeNodes = {}
                ),
                @NamedSubgraph(
                        name = "spacecraftConfImages",
                        attributeNodes = {}
                )
        }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "spacecraft_configuration", schema = "moonkey_db")
public class SpacecraftConfiguration {
    @Id
    @Column(name = "spacecraft_conf_id")
    private Long spacecraftConfId;
    @Basic
    @Column(name = "spacecraft_conf_name")
    private String spacecraftConfName;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id")
    private SpacecraftType spacecraftType;
    @Basic
    @Column(name = "in_use")
    private Boolean inUse;
    @Basic
    @Column(name = "capability")
    private String capability;
    @Basic
    @Column(name = "history")
    private String history;
    @Basic
    @Column(name = "details")
    private String details;
    @Basic
    @Column(name = "maiden_flight")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate maidenFlight;
    @Basic
    @Column(name = "height")
    private Double height;
    @Basic
    @Column(name = "diameter")
    private Double diameter;
    @Basic
    @Column(name = "human_rated")
    private Boolean humanRated;
    @Basic
    @Column(name = "crew_capacity")
    private Integer crewCapacity;
    @Basic
    @Column(name = "payload_capacity")
    private Integer payloadCapacity;
    @Basic
    @Column(name = "payload_return_capacity")
    private Integer payloadReturnCapacity;
    @Basic
    @Column(name = "flight_life")
    private String flightLife;
    @Basic
    @Column(name = "wiki_link")
    private String wikiLink;
    @Basic
    @Column(name = "info_link")
    private String infoLink;
    @ManyToOne
    @JoinColumn(name = "agency_id")
    private Agencies agencies;
    @OneToMany(mappedBy = "spacecraftConfiguration")
    @JsonIgnore
    @BatchSize(size = 10)
    private Set<Spacecraft> spacecrafts;
    @OneToMany(mappedBy = "spacecraftConfiguration")
    @BatchSize(size = 10)
    private Set<SpacecraftConfImages> spacecraftConfImages;

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        SpacecraftConfiguration that = (SpacecraftConfiguration) object;
        return Objects.equals(spacecraftConfId, that.spacecraftConfId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(spacecraftConfId);
    }
}
