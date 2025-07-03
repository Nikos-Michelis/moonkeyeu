package com.moonkeyeu.core.api;

import com.moonkeyeu.core.api.security.repository.PermissionRepository;
import com.moonkeyeu.core.api.security.repository.RoleRepository;
import com.moonkeyeu.core.api.user.model.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;
import java.util.Set;
import java.util.stream.Collectors;

@SpringBootApplication
@EnableJpaAuditing
@EnableScheduling
public class MoonkeyEuCoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(MoonkeyEuCoreApplication.class, args);
    }

    @Bean
    public CommandLineRunner seedDatabase(RoleRepository roleRepository, PermissionRepository permissionRepository) {
        return args -> {
            for (Permissions permissionEnum : Permissions.values()) {
                permissionRepository.findByName(permissionEnum).orElseGet(() -> {
                    Permission permission = Permission.builder()
                            .name(permissionEnum)
                            .description(permissionEnum.getPermission())
                            .build();
                    return permissionRepository.save(permission);
                });
            }
            for (Role roleEnum : Role.values()) {
                roleRepository.findByName(roleEnum.name()).orElseGet(() -> {
                    Set<Permission> permissions = roleEnum.getPermissions().stream()
                            .map(permissionEnum -> permissionRepository.findByName(permissionEnum)
                                    .orElseThrow(() -> new RuntimeException(
                                            "Permission not found: " + permissionEnum.name())))
                            .collect(Collectors.toSet());

                    Roles newRole = Roles.builder()
                            .name(roleEnum.name())
                            .permissions(permissions)
                            .build();
                    return roleRepository.save(newRole);
                });
            }
        };
    }
}
