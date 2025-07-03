package com.moonkeyeu.core.api.launch.model.mission;

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
@Table(name = "orbit", schema = "moonkey_db")
public class Orbit {
    @Id
    @Column(name = "orbit_id")
    private Long orbitId;
    @Basic
    @Column(name = "name")
    private String orbitName;
    @Basic
    @Column(name = "abbrev")
    private String abbrev;
    @BatchSize(size = 10)
    @OneToMany(mappedBy = "orbit")
    private Set<Mission> missions;
    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Orbit orbit = (Orbit) object;
        return Objects.equals(orbitId, orbit.orbitId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orbitId);
    }
}
