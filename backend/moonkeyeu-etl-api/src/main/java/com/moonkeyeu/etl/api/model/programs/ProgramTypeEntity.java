package com.moonkeyeu.etl.api.model.programs;

import jakarta.persistence.*;
import lombok.*;

@Data
@Getter
@Setter
@Entity
@Table(name = "program_type", schema = "moonkey_db")
public class ProgramTypeEntity  {
    @Id
    @Column(name = "type_id")
    private Long type_id;
    @Basic
    @Column(name = "name")
    private String type_name;
}
