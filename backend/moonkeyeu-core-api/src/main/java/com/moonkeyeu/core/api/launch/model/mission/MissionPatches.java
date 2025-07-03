package com.moonkeyeu.core.api.launch.model.mission;

import com.moonkeyeu.core.api.launch.model.launch.Launch;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "mission_patches", schema = "moonkey_db")
public class MissionPatches {
    @Id
    @Column(name = "patch_id")
    private Long patchId;
    @Basic
    @Column(name = "priority")
    private String priority;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "image_url")
    private String imageUrl;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "launch_id")
    private Launch launch;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MissionPatches that = (MissionPatches) o;
        return Objects.equals(patchId, that.patchId);
    }
    @Override
    public int hashCode() {
      return Objects.hash(patchId);
  }
}
