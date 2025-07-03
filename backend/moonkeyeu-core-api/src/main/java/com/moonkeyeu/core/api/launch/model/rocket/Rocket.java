package com.moonkeyeu.core.api.launch.model.rocket;

import com.moonkeyeu.core.api.launch.model.launcher.LauncherStage;
import com.moonkeyeu.core.api.launch.model.spacecraft.SpacecraftStage;
import com.moonkeyeu.core.api.launch.model.launch.Launch;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@NamedEntityGraph(
        name = "rocket-images-agency",
        attributeNodes = {
                @NamedAttributeNode(value = "rocketConfiguration", subgraph = "rocketConfiguration"),
        },
        subgraphs = {
                @NamedSubgraph(
                        name = "rocket",
                        attributeNodes = {
                                @NamedAttributeNode(value = "rocketConfiguration", subgraph = "rocketConfiguration")

                        }
                ),
                @NamedSubgraph(
                        name = "rocketConfiguration",
                        attributeNodes = {
                                @NamedAttributeNode(value = "rocketConfId"),
                                @NamedAttributeNode(value = "rocketName"),
                                @NamedAttributeNode(value = "fullname"),
                                @NamedAttributeNode(value = "maidenFlight"),

                        }
                ),
        }
)
@Entity
public class Rocket {
    @Id
    @Column(name = "rocket_id")
    private Long rocketId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rocket_conf_id")
    private RocketConfiguration rocketConfiguration;
    @OneToMany(mappedBy = "rocket", fetch = FetchType.LAZY)
    @BatchSize(size = 20)
    private Set<SpacecraftStage> spacecraftStages;
    @OneToMany(mappedBy = "rocket", fetch = FetchType.LAZY)
    @BatchSize(size = 20)
    private Set<LauncherStage> launcherStages;
    @OneToMany(mappedBy = "rocket", fetch = FetchType.LAZY)
    @BatchSize(size = 20)
    private Set<Launch> launches;

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Rocket rocket = (Rocket) object;
        return Objects.equals(rocketId, rocket.rocketId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rocketId);
    }
}
