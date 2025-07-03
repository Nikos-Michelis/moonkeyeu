package com.moonkeyeu.etl.api.model.pad;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Entity
@Table(name = "launch_pad_images", schema = "moonkey_db")
public class LaunchPadImagesEntity  {
    @Id
    @Column(name = "image_id", nullable = false)
    private Long image_id;
    @Basic
    @Column(name = "image_name", nullable = true, length = 255)
    private String image_name;
    @Basic
    @Column(name = "image_url", nullable = true, length = 500)
    private String image_url;
    @Basic
    @Column(name = "thumbnail_url", nullable = true, length = 500)
    private String thumbnail_url;
    @Basic
    @Column(name = "credit", nullable = true, length = 255)
    private String credit;
    @Basic
    @Column(name = "launch_pad_id", nullable = false)
    private Integer launch_pad_id;
}
