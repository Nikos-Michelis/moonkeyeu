package com.moonkeyeu.core.api.security.model.otp;

import com.moonkeyeu.core.api.user.model.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "otp", schema = "moonkey_db")
public class OtpToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "otp_id")
    private Long otpId;
    @Column(name = "otp_type")
    @Enumerated(EnumType.STRING)
    private OtpType otpType;
    @Column(name = "token")
    private String token;
    @Column(name = "otp_code")
    private String otp;
    @Column(name = "expired")
    private boolean expired;
    @Column(name = "revoked")
    private boolean revoked;
    @Column(name = "redeemed")
    private boolean isRedeemed;
    @Column(name = "expires_at")
    private Instant expiresAt;
    @Column(name = "validated_at")
    private Instant validatedAt;
    @CreatedDate
    @Column(name = "created_at", nullable = false, unique = false)
    private Instant createdAt;
    @LastModifiedDate
    @Column(name = "updated_at", nullable = false, unique = false)
    private Instant updatedAt;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;
}
