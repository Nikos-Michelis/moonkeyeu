package com.moonkeyeu.etl.api.model.media;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Entity
@Table(name = "mission_patches", schema = "moonkey_db")
public class MissionPatchesEntity  {
    @Id
    @Column(name = "patch_id")
    private Long patch_id;
    @Column(name = "priority")
    private String priority;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "image_url")
    private String image_url;
    @Basic
    @Column(name = "launch_id")
    private String launch_id;
}
