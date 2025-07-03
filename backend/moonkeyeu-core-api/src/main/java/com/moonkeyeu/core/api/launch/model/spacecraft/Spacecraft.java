package com.moonkeyeu.core.api.launch.model.spacecraft;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;

import java.util.Objects;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@NamedEntityGraph(
        name = "spacecraft-images",
        attributeNodes = {
                @NamedAttributeNode(value = "status", subgraph = "status"),
                @NamedAttributeNode(value = "spacecraftConfiguration", subgraph = "spacecraftConfiguration"),

        },
        subgraphs = {
                @NamedSubgraph(
                        name = "status",
                        attributeNodes = {
                                @NamedAttributeNode(value = "statusId"),
                                @NamedAttributeNode(value = "statusName")

                        }
                ),
                @NamedSubgraph(
                        name = "spacecraftConfiguration",
                        attributeNodes = {
                                @NamedAttributeNode(value = "spacecraftConfImages", subgraph = "spacecraftConfImages"),
                                @NamedAttributeNode(value = "maidenFlight")
                        }
                ),
                 @NamedSubgraph(
                        name = "spacecraftConfImages",
                        attributeNodes = {
                                @NamedAttributeNode(value = "imageId"),
                                @NamedAttributeNode(value = "imageName"),
                                @NamedAttributeNode(value = "imageUrl"),
                                @NamedAttributeNode(value = "thumbnailUrl"),
                                @NamedAttributeNode(value = "credit")
                        }
                )
        }
)
@Entity
public class Spacecraft {
    @Id
    @Column(name = "spacecraft_id")
    private Long spacecraftId;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "serial_number")
    private String serialNumber;
    @Basic
    @Column(name = "is_placeholder")
    private Boolean isPlaceholder;
    @Basic
    @Column(name = "in_space")
    private Boolean inSpace;
    @Basic
    @Column(name = "flights_count")
    private Integer flightsCount;
    @Basic
    @Column(name = "mission_ends_count")
    private Integer missionEndsCount;
    @Basic
    @Column(name = "description")
    private String description;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "spacecraft_conf_id")
    private SpacecraftConfiguration spacecraftConfiguration;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id")
    @JsonBackReference
    private SpacecraftStatus status;
    @OneToMany(mappedBy = "spacecraft")
    @BatchSize(size = 10)
    private Set<SpacecraftStage> spacecraftStages;

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Spacecraft that = (Spacecraft) object;
        return Objects.equals(spacecraftId, that.spacecraftId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(spacecraftId);
    }
}
