package com.moonkeyeu.etl.api.model.programs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

@Data
@Getter
@Setter
@Entity
@Table(name = "programs_has_agencies", schema = "moonkey_db", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"program_id", "agency_id"})
})
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProgramsHasAgenciesEntity {
    @Id
    @Column(name = "program_agency_id")
    private Long id;
    @Basic
    @Column(name = "program_id")
    private Integer program_id;
    @Basic
    @Column(name = "agency_id")
    private Integer agency_id;
}
