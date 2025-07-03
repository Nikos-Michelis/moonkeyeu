package com.moonkeyeu.core.api.launch.model.rocket;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;

import java.util.Objects;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "rocket_conf_images", schema = "moonkey_db")
public class RocketConfImages {

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

    @OneToMany(mappedBy = "rocketConfImages")
    @BatchSize(size = 100)
    private Set<RocketConfiguration> rocketConfigurations;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RocketConfImages that = (RocketConfImages) o;
        return Objects.equals(imageId, that.imageId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(imageId);
    }
}
