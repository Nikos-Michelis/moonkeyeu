package com.moonkeyeu.etl.api.model.spacecraft;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Entity
@Table(name = "spacecraft_stage", schema = "moonkey_db")
@JsonIgnoreProperties(ignoreUnknown = true)
public class SpacecraftStageEntity  {
    @Id
    @Column(name = "spacecraft_stage_id")
    private Long spacecraft_stage_id;
    @Basic
    @Column(name = "mission_end")
    private String mission_end;
    @Basic
    @Column(name = "destination")
    private String destination;
    @Column(name = "rocket_id")
    private Integer rocket_id;
    @Column(name = "spacecraft_id")
    private Integer spacecraft_id;
    @Column(name = "landing_id")
    private Integer landing_id;
}
