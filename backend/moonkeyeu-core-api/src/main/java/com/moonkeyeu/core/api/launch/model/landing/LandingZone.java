package com.moonkeyeu.core.api.launch.model.landing;

import com.moonkeyeu.core.api.launch.model.pad.Location;
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
@Table(name = "landing_zone", schema = "moonkey_db")
public class LandingZone {
    @Id
    @Column(name = "landing_zone_id")
    private Long landingZoneId;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "abbrev")
    private String abbrev;
    @Basic
    @Column(name = "description")
    private String description;
    @Basic
    @Column(name = "successful_landings")
    private Integer successfulLandings;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
    private Location location;
    @OneToMany(mappedBy = "landingZone")
    @BatchSize(size = 10)
    private Set<Landing> landings;

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        LandingZone that = (LandingZone) object;
        return Objects.equals(landingZoneId, that.landingZoneId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(landingZoneId);
    }
}
