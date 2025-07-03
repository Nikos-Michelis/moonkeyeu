package com.moonkeyeu.core.api.security.model.otp;

import com.moonkeyeu.core.api.user.model.User;
import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;

@Getter
@Setter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "otp_resend", schema = "moonkey_db")
public class OtpResend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "resend_id")
    private Long resendId;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;
    @Basic
    @Column(name = "otp_type")
    private String otpType;
    @Basic
    @Column(name = "otp_resend_count")
    private int otpResendCount;
    @Basic
    @Column(name = "last_otp_sent_time")
    private Instant lastOtpSentTime;
}
