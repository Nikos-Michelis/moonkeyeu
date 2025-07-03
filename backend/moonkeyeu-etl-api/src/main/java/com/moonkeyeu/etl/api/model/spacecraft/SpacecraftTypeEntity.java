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
@Table(name = "spacecraft_type", schema = "moonkey_db")
@JsonIgnoreProperties(ignoreUnknown = true)
public class SpacecraftTypeEntity  {
    @Id
    @Column(name = "type_id")
    private Long type_id;
    @Basic
    @Column(name = "name")
    private String name;

}
