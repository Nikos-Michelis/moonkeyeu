package com.moonkeyeu.core.api.security.repository;

import com.moonkeyeu.core.api.security.model.SignUpMethods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProviderRepository extends JpaRepository<SignUpMethods, Long> {
    Optional<SignUpMethods> findByProvider(String provider);
}
