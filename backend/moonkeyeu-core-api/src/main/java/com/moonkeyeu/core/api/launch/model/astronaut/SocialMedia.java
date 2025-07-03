package com.moonkeyeu.core.api.launch.model.astronaut;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "social_media", schema = "moonkey_db")
public class SocialMedia {
    @Id
    @Column(name = "social_id")
    private Long socialId;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "media_url")
    private String mediaUrl;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "astronaut_id")
    @JsonBackReference
    private Astronaut astronaut;

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        SocialMedia that = (SocialMedia) object;
        return Objects.equals(socialId, that.socialId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(socialId);
    }
}
