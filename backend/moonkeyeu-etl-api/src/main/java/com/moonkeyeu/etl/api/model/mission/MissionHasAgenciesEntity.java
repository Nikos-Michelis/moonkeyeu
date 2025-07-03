package com.moonkeyeu.etl.api.model.mission;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Entity
@Table(name = "mission_has_agencies", schema = "moonkey_db", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"mission_id", "agency_id"})
})
@JsonIgnoreProperties(ignoreUnknown = true)
public class MissionHasAgenciesEntity {
    @Id
    @Column(name = "mission_agencies_id")
    private Long id;
    @Basic
    @Column(name = "mission_id")
    private Long mission_id;
    @Basic
    @Column(name = "agency_id")
    private Long agency_id;
}
