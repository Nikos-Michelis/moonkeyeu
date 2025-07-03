package com.moonkeyeu.etl.api.model.launch;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Entity
@Table(name = "launch_images", schema = "moonkey_db")
@JsonIgnoreProperties(ignoreUnknown = true)
public class LaunchImagesEntity {
    @Id
    @Column(name = "image_id")
    private Integer image_id;
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
}
