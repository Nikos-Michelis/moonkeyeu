package com.moonkeyeu.core.api.user.model;

import com.moonkeyeu.core.api.security.model.SignUpMethods;
import com.moonkeyeu.core.api.security.model.otp.OtpResend;
import com.moonkeyeu.core.api.security.model.otp.OtpToken;
import com.moonkeyeu.core.api.security.model.token.jwt.Token;
import com.moonkeyeu.core.api.security.model.token.reset.ResetToken;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Principal;
import java.time.Instant;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode(of = "userId")
public class User implements UserDetails, Principal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "username", unique = true)
    private String username;
    @Column(name="email", unique = true)
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "enable")
    private boolean enable;
    @Column(name = "account_locked")
    private boolean accountLocked;
    @Column(name = "blocks")
    private Integer blocks;
    @Column(name = "attempts")
    private Integer attempts;
    @Column(name = "locked_at")
    private Instant lockedAt;
    @Column(name = "lock_expires_at")
    private Instant lockExpiresAt;
    @Column(name = "validated_at")
    private Instant validatedAt;
    @CreatedDate
    @Column(name = "created_at", nullable = false, unique = false)
    private Instant createdAt;
    @LastModifiedDate
    @Column(name = "updated_at", nullable = false, unique = false)
    private Instant updatedAt;
    @BatchSize(size = 20)
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Roles> roles;
    @OneToMany(orphanRemoval = true, mappedBy = "user")
    @BatchSize(size = 20)
    private List<Token> tokens;
    @OneToMany(orphanRemoval = true, mappedBy = "user")
    @BatchSize(size = 20)
    private List<OtpToken> otpTokens;
    @OneToMany(orphanRemoval = true, mappedBy = "user")
    @BatchSize(size = 20)
    private List<OtpResend> otpResends;
    @OneToMany(orphanRemoval = true, mappedBy = "user")
    @BatchSize(size = 20)
    private List<ResetToken> resetTokens;
    @BatchSize(size = 50)
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_entry_method",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "method_id")
    )
    private Set<SignUpMethods> signUpMethods = new HashSet<>();
    @OneToMany(orphanRemoval = true, mappedBy = "user")
    @BatchSize(size = 20)
    private List<Bookmark> bookmarks;

    public void addSignUpProvider(SignUpMethods signUpMethod) {
        this.signUpMethods.add(signUpMethod);
    }
    public void removeAllRoles() {
        this.roles.forEach(role -> role.getUsers().remove(this));
        this.roles.clear();
    }

    public void removeAllSignUpMethods() {
        this.signUpMethods.forEach(method -> method.getUsers().remove(this));
        this.signUpMethods.clear();
    }

    @Override
    public String getName() {
        return email;
    }

   @Override
   public Collection<? extends GrantedAuthority> getAuthorities() {
       return roles.stream()
               .flatMap(role -> {
                   Set<SimpleGrantedAuthority> authorities = role.getPermissions().stream()
                           .map(permission -> new SimpleGrantedAuthority(permission.getName().getPermission()))
                           .collect(Collectors.toSet());

                   authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
                   return authorities.stream();
               })
               .collect(Collectors.toSet());
   }

   @Override
   public String getPassword() {
        return password;
    }
   public String getNickname() { return username; }
    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !accountLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enable;
    }
}
