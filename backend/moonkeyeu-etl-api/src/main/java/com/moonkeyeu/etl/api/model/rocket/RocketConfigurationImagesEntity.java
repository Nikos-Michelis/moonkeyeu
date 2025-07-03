package com.moonkeyeu.etl.api.model.rocket;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Entity
@Table(name = "rocket_conf_images", schema = "moonkey_db")
public class RocketConfigurationImagesEntity {
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
    @Getter
    @Basic
    @Column(name = "credit")
    private String credit;
}
