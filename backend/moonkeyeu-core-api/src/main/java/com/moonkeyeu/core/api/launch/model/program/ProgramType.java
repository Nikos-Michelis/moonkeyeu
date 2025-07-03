package com.moonkeyeu.core.api.launch.model.program;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "program_type", schema = "moonkey_db")
public class ProgramType {
    @Id
    @Column(name = "type_id")
    private Long typeId;
    @Basic
    @Column(name = "name")
    private String typeName;
    @OneToMany(mappedBy = "type")
    private Set<Programs> programs;
    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        ProgramType that = (ProgramType) object;
        return Objects.equals(typeId, that.typeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(typeId);
    }
}
