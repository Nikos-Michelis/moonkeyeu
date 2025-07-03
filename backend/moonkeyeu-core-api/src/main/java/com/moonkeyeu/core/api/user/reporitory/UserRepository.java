package com.moonkeyeu.core.api.user.reporitory;

import com.moonkeyeu.core.api.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("""
        SELECT u
        FROM User u
        INNER JOIN FETCH u.signUpMethods s
        INNER JOIN FETCH u.roles r
        LEFT JOIN FETCH r.permissions p
        WHERE u.email = :email
    """)
    Optional<User> findByEmail(@Param("email") String email);
    @Query("""
        SELECT COUNT(u) > 0
        FROM User u
        WHERE u.email = :email
    """)
    boolean existsByEmail(@Param("email") String email);
    @Query("""
        SELECT COUNT(u) > 0
        FROM User u
        WHERE u.username = :username
    """)
    boolean existsByUsername(@Param("username") String username);
}
