package com.moonkeyeu.core.api.launch.model.launcher;

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
@Table(name = "launcher_status", schema = "moonkey_db")
public class LauncherStatus {
    @Id
    @Column(name = "status_id")
    private Long statusId;
    @Basic
    @Column(name = "name")
    private String statusName;
    @OneToMany(mappedBy = "status")
    @BatchSize(size = 10)
    private Set<Launcher> launchers;
    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        LauncherStatus that = (LauncherStatus) object;
        return Objects.equals(statusId, that.statusId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(statusId);
    }
}
