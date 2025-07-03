package com.moonkeyeu.core.api.launch.model.pad;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "launch_pad_images", schema = "moonkey_db")
public class LaunchPadImages {
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
    @JoinColumn(name = "launch_pad_id")
    private LaunchPad launchPad;

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        LaunchPadImages that = (LaunchPadImages) object;
        return Objects.equals(imageId, that.imageId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(imageId);
    }
}
