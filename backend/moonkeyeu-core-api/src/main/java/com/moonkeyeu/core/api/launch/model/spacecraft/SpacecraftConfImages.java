package com.moonkeyeu.core.api.launch.model.spacecraft;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "spacecraft_conf_images", schema = "moonkey_db")
public class SpacecraftConfImages {
    @Id
    @Column(name = "image_id")
    private Long imageId;
    @Basic
    @Column(name = "image_name")
    private String imageName;
    @Basic
    @Column(name = "image_url")
    private String imageUrl;
    @Basic
    @Column(name = "thumbnail_url")
    private String thumbnailUrl;
    @Basic
    @Column(name = "credit")
    private String credit;
    @ManyToOne
    @JoinColumn(name = "spacecraft_conf_id")
    private SpacecraftConfiguration spacecraftConfiguration;

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        SpacecraftConfImages that = (SpacecraftConfImages) object;
        return Objects.equals(imageId, that.imageId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(imageId);
    }
}
