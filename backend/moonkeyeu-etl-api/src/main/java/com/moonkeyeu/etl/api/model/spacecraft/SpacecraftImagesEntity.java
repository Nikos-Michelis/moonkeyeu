package com.moonkeyeu.etl.api.model.spacecraft;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Entity
@Table(name = "spacecraft_conf_images", schema = "moonkey_db")
public class SpacecraftImagesEntity  {
    @Id
    @Column(name = "image_id")
    private Long image_id;
    @Basic
    @Column(name = "image_name")
    private String image_name;
    @Basic
    @Column(name = "image_url")
    private String image_url;
    @Basic
    @Column(name = "thumbnail_url")
    private String thumbnail_url;
    @Basic
    @Column(name = "credit")
    private String credit;
    @Column(name = "spacecraft_conf_id")
    private Integer spacecraft_conf_id;
}
