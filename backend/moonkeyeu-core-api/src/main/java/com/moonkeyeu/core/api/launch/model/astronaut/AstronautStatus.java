package com.moonkeyeu.core.api.launch.model.astronaut;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "astronaut_status", schema = "moonkey_db")
public class AstronautStatus {
    @Id
    @Column(name = "status_id")
    private Long statusId;
    @Basic
    @Column(name = "name")
    private String statusName;
    @OneToMany(mappedBy = "status", fetch = FetchType.LAZY)
    @JsonManagedReference
    private Set<Astronaut> astronauts;


    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        AstronautStatus that = (AstronautStatus) object;
        return Objects.equals(statusId, that.statusId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(statusId);
    }
}
