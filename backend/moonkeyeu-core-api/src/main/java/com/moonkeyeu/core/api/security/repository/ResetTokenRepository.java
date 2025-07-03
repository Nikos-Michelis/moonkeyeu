package com.moonkeyeu.core.api.security.repository;

import com.moonkeyeu.core.api.security.model.token.reset.ResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResetTokenRepository extends JpaRepository<ResetToken, Long> {
    Optional<ResetToken> findByToken(String token);
    @Query(value = """
      SELECT t
      FROM ResetToken t
      INNER JOIN User u ON t.user.userId = u.userId
      WHERE u.userId = :id AND (t.expired = false or t.revoked = false or t.isRedeemed = false)
      """)
    List<ResetToken> findAllValidResetTokenByUser(Long id);
}
