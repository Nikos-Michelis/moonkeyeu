package com.moonkeyeu.core.api.launch.model.program;

import com.moonkeyeu.core.api.launch.model.launch.Launch;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "launch_has_programs", schema = "moonkey_db")
public class LaunchHasPrograms {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "launch_programs_id")
    private Long launchProgramsId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "program_id")
    private Programs programId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "launch_id")
    private Launch launchId;
}
