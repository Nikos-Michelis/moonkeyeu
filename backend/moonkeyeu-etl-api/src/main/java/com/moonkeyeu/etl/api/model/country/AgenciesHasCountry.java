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
@Table(name = "agencies_has_country", schema = "moonkey_db", uniqueConstraints = {
        @UniqueConstraint(name = "UniqueNumberAndStatus", columnNames = {"agency_id", "country_id"})
})
@JsonIgnoreProperties(ignoreUnknown = true)
public class AgenciesHasCountry {
    @Id
    @Column(name = "agencies_country_id")
    private Long id;
    @Basic
    @Column(name = "agency_id")
    private Long agency_id;
    @Basic
    @Column(name = "country_id")
    private Long country_id;
}
