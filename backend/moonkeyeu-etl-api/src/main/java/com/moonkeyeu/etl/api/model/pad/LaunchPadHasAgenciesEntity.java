package com.moonkeyeu.etl.api.model.pad;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Entity
@Table(name = "pad_has_agencies", schema = "moonkey_db", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"launch_pad_id", "agency_id"})
})
@JsonIgnoreProperties(ignoreUnknown = true)
public class LaunchPadHasAgenciesEntity {
    @Id
    @Column(name = "pad_agency_id")
    private Long id;
    @Basic
    @Column(name = "launch_pad_id")
    private Long launch_pad_id;
    @Basic
    @Column(name = "agency_id")
    private Long agency_id;
}
