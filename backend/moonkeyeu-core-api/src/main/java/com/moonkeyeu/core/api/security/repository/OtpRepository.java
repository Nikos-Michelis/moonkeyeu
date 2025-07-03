package com.moonkeyeu.core.api.security.repository;

import com.moonkeyeu.core.api.security.model.otp.OtpToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OtpRepository extends JpaRepository<OtpToken, Long> {
    Optional<OtpToken> findByToken(String otp);

    @Query(value = """
      SELECT t
      FROM OtpToken t
      INNER JOIN User u ON t.user.userId = u.userId
      WHERE u.userId = :id AND (t.expired = false or t.revoked = false or t.isRedeemed = false)
      """)
    List<OtpToken> findAllValidOtpByUser(Long id);

}
