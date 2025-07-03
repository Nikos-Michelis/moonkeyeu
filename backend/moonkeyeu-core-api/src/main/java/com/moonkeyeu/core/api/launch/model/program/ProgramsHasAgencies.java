package com.moonkeyeu.core.api.launch.model.program;

import com.moonkeyeu.core.api.launch.model.agency.Agencies;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "programs_has_agencies", schema = "moonkey_db")
@EqualsAndHashCode(of = "programAgencyId")
public class ProgramsHasAgencies {
    @Id
    @Column(name = "program_agency_id")
    private Long programAgencyId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "program_id")
    private Programs programId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agency_id")
    private Agencies agencyId;
}
