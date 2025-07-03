package com.moonkeyeu.core.api.launch.model.country;

import com.moonkeyeu.core.api.launch.model.astronaut.Astronaut;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "astronaut_has_country", schema = "moonkey_db")
public class AstronautHasCountry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "astronaut_country_id")
    private Long astronautCountryId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "astronaut_id")
    private Astronaut astronaut;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id")
    private Country country;

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        AstronautHasCountry that = (AstronautHasCountry) object;
        return Objects.equals(astronautCountryId, that.astronautCountryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(astronautCountryId);
    }
}
