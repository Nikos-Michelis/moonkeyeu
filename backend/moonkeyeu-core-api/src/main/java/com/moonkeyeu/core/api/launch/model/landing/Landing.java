package com.moonkeyeu.core.api.launch.model.landing;

import com.moonkeyeu.core.api.launch.model.launcher.LauncherStage;
import com.moonkeyeu.core.api.launch.model.spacecraft.SpacecraftStage;
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
@Entity
@Table(name = "landing", schema = "moonkey_db")
public class Landing {
    @Id
    @Column(name = "landing_id")
    private Long landingId;
    @Basic
    @Column(name = "attempt")
    private Byte attempt;
    @Basic
    @Column(name = "success")
    private String success;
    @Basic
    @Column(name = "description")
    private String description;
    @Basic
    @Column(name = "downrange_distance")
    private Integer downrangeDistance;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "landing_zone_id")
    private LandingZone landingZone;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "landing_type_id")
    private LandingType landingType;
    @OneToMany(mappedBy = "landing", fetch = FetchType.LAZY)
    @BatchSize(size = 50)
    private Set<LauncherStage> launcherStages;
    @OneToMany(mappedBy = "landing", fetch = FetchType.LAZY)
    @BatchSize(size = 50)
    private Set<SpacecraftStage> spacecraftStages;

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Landing landing = (Landing) object;
        return Objects.equals(landingId, landing.landingId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(landingId);
    }
}
