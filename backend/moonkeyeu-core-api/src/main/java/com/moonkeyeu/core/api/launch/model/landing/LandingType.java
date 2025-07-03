package com.moonkeyeu.core.api.launch.model.landing;

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
@Table(name = "landing_type", schema = "moonkey_db")
public class LandingType {
    @Id
    @Column(name = "landing_type_id")
    private Long landingTypeId;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "abbrev")
    private String abbrev;
    @Basic
    @Column(name = "description")
    private String description;
    @OneToMany(mappedBy = "landingType")
    @BatchSize(size = 10)
    private Set<Landing> landings;

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        LandingType that = (LandingType) object;
        return Objects.equals(landingTypeId, that.landingTypeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(landingTypeId);
    }
}
