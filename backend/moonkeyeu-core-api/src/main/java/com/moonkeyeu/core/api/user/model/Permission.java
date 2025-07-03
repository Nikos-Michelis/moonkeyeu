package com.moonkeyeu.core.api.user.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "permissions")
@EqualsAndHashCode(of = "permissionId")
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "permission_id")
    private Long permissionId;
    @Column(name = "name", unique = true, nullable = false)
    @Enumerated(EnumType.STRING)
    private Permissions name;
    @Column(name = "description")
    private String description;
    @ManyToMany(mappedBy = "permissions")
    @JsonIgnore
    @BatchSize(size = 10)
    private Set<Roles> roles;

}