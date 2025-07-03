package com.moonkeyeu.core.api.security.model.token.reset;

import com.moonkeyeu.core.api.user.model.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;

@Data
@Entity
@NoArgsConstructor
@Table(name = "reset_token")
public class ResetToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "token_id")
    private Long id;
    @Column(name = "token", unique = true)
    private String token;
    @Column(name = "expired")
    private boolean expired;
    @Column(name = "revoked")
    private boolean revoked;
    @Column(name = "redeemed")
    private boolean isRedeemed;
    @Column(name = "validated_at")
    private Instant validatedAt;
    @Column(name = "expires_at")
    private Instant expiresAt;
    @CreatedDate
    @Column(name = "issued_at")
    private Instant issuedAt;
    @LastModifiedDate
    @Column(name = "updated_at", insertable = false, nullable = false, unique = false)
    private Instant updatedAt;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    public User user;
    public ResetToken(String token, Instant expiresAt, Instant issuedAt, User user) {
        this.token = token;
        this.expiresAt = expiresAt;
        this.issuedAt = issuedAt;
        this.user = user;
    }
}
