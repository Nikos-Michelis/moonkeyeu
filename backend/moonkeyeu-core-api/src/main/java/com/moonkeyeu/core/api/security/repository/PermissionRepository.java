package com.moonkeyeu.core.api.security.repository;

import com.moonkeyeu.core.api.user.model.Permission;
import com.moonkeyeu.core.api.user.model.Permissions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {
    Optional<Permission> findByName(Permissions name);
}
