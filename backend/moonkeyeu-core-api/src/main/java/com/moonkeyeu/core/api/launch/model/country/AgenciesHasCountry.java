package com.moonkeyeu.core.api.launch.model.country;

import com.moonkeyeu.core.api.launch.model.agency.Agencies;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "agencies_has_country", schema = "moonkey_db")
public class AgenciesHasCountry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "agencies_country_id")
    private Long agenciesCountryId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agency_id")
    private Agencies agency;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id")
    private Country country;

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        AgenciesHasCountry that = (AgenciesHasCountry) object;
        return Objects.equals(agenciesCountryId, that.agenciesCountryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(agenciesCountryId);
    }
}
