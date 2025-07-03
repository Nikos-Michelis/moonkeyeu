package com.moonkeyeu.core.api.security.repository;

import com.moonkeyeu.core.api.security.model.token.jwt.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    @Query("""
        SELECT t
        FROM Token t
        INNER JOIN FETCH t.user u
        INNER JOIN FETCH u.roles r
        LEFT JOIN FETCH r.permissions
        WHERE u.userId = :id AND (t.expired = false OR t.revoked = false)
    """)
    List<Token> findAllValidTokenByUser(@Param("id") Long id);

    Optional<Token> findByToken(String token);
}
