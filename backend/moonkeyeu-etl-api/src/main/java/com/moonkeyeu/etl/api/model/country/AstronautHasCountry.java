package com.moonkeyeu.etl.api.model.country;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Entity
@Table(name = "astronaut_has_country", schema = "moonkey_db", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"astronaut_id", "country_id"})
})
@JsonIgnoreProperties(ignoreUnknown = true)
public class AstronautHasCountry {
    @Id
    @Column(name = "astronaut_country_id")
    private Long id;
    @Basic
    @Column(name = "astronaut_id")
    private Long astronaut_id;
    @Basic
    @Column(name = "country_id")
    private Long country_id;
}
