package com.moonkeyeu.core.api.launch.model.launch;

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
@Table(name = "launch_status", schema = "moonkey_db")
public class LaunchStatus {
    @Id
    @Column(name = "status_id")
    private Long statusId;
    @Basic
    @Column(name = "name")
    private String statusName;
    @Basic
    @Column(name = "abbrev")
    private String abbrev;
    @Basic
    @Column(name = "description")
    private String description;
    @OneToMany(mappedBy = "launchStatus")
    @BatchSize(size = 10)
    private Set<Launch> launches;


    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        LaunchStatus that = (LaunchStatus) object;
        return Objects.equals(statusId, that.statusId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(statusId);
    }
}
