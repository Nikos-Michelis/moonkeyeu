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
@Table(name = "spacecraft", schema = "moonkey_db")
@JsonIgnoreProperties(ignoreUnknown = true)
public class SpacecraftEntity  {
    @Id
    @Column(name = "spacecraft_id")
    private Long spacecraft_id;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "serial_number")
    private String serial_number;
    @Basic
    @Column(name = "is_placeholder", columnDefinition = "TINYINT(1)")
    private Boolean is_placeholder;
    @Basic
    @Column(name = "in_space", columnDefinition = "TINYINT(1)")
    private Boolean in_space;
    @Basic
    @Column(name = "flights_count")
    private Integer flights_count;
    @Basic
    @Column(name = "mission_ends_count")
    private Integer mission_ends_count;
    @Basic
    @Column(name = "description")
    private String description;
    private Integer spacecraft_conf_id;
    @Basic
    @Column(name = "status_id")
    private Integer status_id;
}
