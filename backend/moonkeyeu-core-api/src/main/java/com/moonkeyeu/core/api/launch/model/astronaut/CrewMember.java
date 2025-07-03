package com.moonkeyeu.core.api.launch.model.astronaut;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.moonkeyeu.core.api.launch.model.launch.Launch;
import com.moonkeyeu.core.api.launch.model.spacecraft.SpacecraftStage;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "crew_member", schema = "moonkey_db")
public class CrewMember {
    @Id
    @Column(name = "crew_member_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "astronaut_id")
    @JsonBackReference
    private Astronaut astronaut;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private Role role;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "spacecraft_stage_id")
    private SpacecraftStage spacecraftStage;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "launch_id")
    private Launch launch;

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        CrewMember that = (CrewMember) object;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
