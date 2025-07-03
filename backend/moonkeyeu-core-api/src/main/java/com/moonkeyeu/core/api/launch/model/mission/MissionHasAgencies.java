package com.moonkeyeu.core.api.launch.model.mission;

import com.moonkeyeu.core.api.launch.model.agency.Agencies;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "mission_has_agencies", schema = "moonkey_db")
public class MissionHasAgencies {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mission_agencies_id")
    private Long missionAgenciesId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mission_id")
    private Mission mission;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agency_id")
    private Agencies agencies;

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        MissionHasAgencies that = (MissionHasAgencies) object;
        return Objects.equals(missionAgenciesId, that.missionAgenciesId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(missionAgenciesId);
    }
}
