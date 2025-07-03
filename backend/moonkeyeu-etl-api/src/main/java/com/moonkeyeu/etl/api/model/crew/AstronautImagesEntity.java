package com.moonkeyeu.etl.api.model.crew;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Entity
@Table(name = "astronaut_images", schema = "moonkey_db")
public class AstronautImagesEntity  {
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
    @Basic
    @Column(name = "astronaut_id")
    private Integer astronaut_id;
}
