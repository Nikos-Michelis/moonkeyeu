package com.moonkeyeu.core.api.security.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.moonkeyeu.core.api.user.model.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "entry_methods")
@EqualsAndHashCode(of = "id")
public class SignUpMethods {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "method_id")
    private Long id;
    @Column(name = "provider")
    private String provider;
    @ManyToMany(mappedBy = "signUpMethods")
    @JsonIgnore
    private Set<User> users = new HashSet<>();
    @CreatedDate
    @Column(name = "created_at", nullable = false, unique = false)
    private Instant createdAt;
    @LastModifiedDate
    @Column(name = "updated_at", nullable = false, unique = false)
    private Instant updatedAt;
    public void deleteSignUpMethods(SignUpMethods signUpMethods) {
        signUpMethods.getUsers().removeIf(method -> method.getSignUpMethods().remove(signUpMethods));
        this.users.clear();
    }
}
