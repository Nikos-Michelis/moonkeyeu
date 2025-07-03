package com.moonkeyeu.etl.api.model.crew;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Entity
@Table(name = "role", schema = "moonkey_db")
public class RoleEntity  {
    @Id
    @Column(name = "role_id")
    private Long role_id;
    @Basic
    @Column(name = "name")
    @JsonProperty("role")
    private String name;
}
