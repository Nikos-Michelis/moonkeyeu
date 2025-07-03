package com.moonkeyeu.core.api.security.model.token.jwt;

import com.moonkeyeu.core.api.user.model.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "token", schema = "moonkey_db")
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "token_id", unique = true)
    private Integer id;
    @Column(name = "jti")
    private String jti;
    @Column(name = "token", unique = true)
    private String token;
    @Enumerated(EnumType.STRING)
    @Column(name = "token_type")
    private TokenType tokenType = TokenType.BEARER;
    @Enumerated(EnumType.STRING)
    @Column(name = "token_scope")
    private TokenScope tokenScope;
    @Column(name = "revoked")
    public boolean revoked;
    @Column(name = "expired")
    public boolean expired;
    @Column(name = "expires_at", unique = false)
    private Instant expiresAt;
    @CreatedDate
    @Column(name = "issued_at", unique = false)
    private Instant issuedAt;
    @LastModifiedDate
    @Column(name = "updated_at", insertable = false, nullable = false, unique = false)
    private Instant updatedAt;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}