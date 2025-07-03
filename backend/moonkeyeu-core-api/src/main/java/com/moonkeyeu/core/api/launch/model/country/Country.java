package com.moonkeyeu.core.api.launch.model.country;

import com.moonkeyeu.core.api.launch.model.agency.Agencies;
import com.moonkeyeu.core.api.launch.model.astronaut.Astronaut;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;

import java.util.Objects;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "country", schema = "moonkey_db")
public class Country {
    @Id
    @Column(name = "country_id")
    private Long countryId;
    @Basic
    @Column(name = "name")
    private String countryName;
    @Basic
    @Column(name = "alpha_2_code")
    private String alpha2Code;
    @Basic
    @Column(name = "alpha_3_code")
    private String alpha3Code;
    @Basic
    @Column(name = "nationality_name")
    private String nationalityName;
    @Basic
    @Column(name = "nationality_name_composed")
    private String nationalityNameComposed;
    @ManyToMany(mappedBy = "countries")
    @BatchSize(size = 20)
    private Set<Agencies> agencies;
    @ManyToMany(mappedBy = "countries")
    @BatchSize(size = 10)
    private Set<Astronaut> astronauts;

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Country country = (Country) object;
        return Objects.equals(countryId, country.countryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(countryId);
    }
}
