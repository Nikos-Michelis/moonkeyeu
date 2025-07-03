package com.moonkeyeu.etl.api.model.programs;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "launch_has_programs", schema = "moonkey_db", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"program_id", "launch_id"})
})

public class LaunchHasProgramsEntity {
    @Id
    @Column(name = "launch_programs_id")
    private String id;
    @Basic
    @Column(name = "program_id")
    private Integer program_id;
    @Basic
    @Column(name = "launch_id")
    private String launch_id;
}
