package com.moonkeyeu.core.api.launch.model.agency;

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
@Table(name = "agency_type", schema = "moonkey_db")
public class AgencyType {
    @Id
    @Column(name = "type_id")
    private Long typeId;
    @Basic
    @Column(name = "name")
    private String typeName;
    @OneToMany(mappedBy = "agencyType")
    @BatchSize(size = 20)
    private Set<Agencies> agencies;

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        AgencyType that = (AgencyType) object;
        return Objects.equals(typeId, that.typeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(typeId);
    }
}
