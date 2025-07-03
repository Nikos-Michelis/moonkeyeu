package com.moonkeyeu.etl.api.model.crew;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Entity
@Table(name = "crew_member", schema = "moonkey_db")
@JsonIgnoreProperties(ignoreUnknown = true)
public class CrewMemberEntity  {
    @Id
    @Column(name = "crew_member_id", nullable = false)
    private Long crew_member_id;
    @Basic
    @Column(name = "astronaut_id", nullable = false)
    private Integer astronaut_id;
    @Basic
    @Column(name = "role_id", nullable = false)
    private Integer role_id;
    @Basic
    @Column(name = "spacecraft_stage_id", nullable = false)
    private Integer spacecraft_stage_id;
    @Basic
    @Column(name = "launch_id")
    private String launch_id;
}
