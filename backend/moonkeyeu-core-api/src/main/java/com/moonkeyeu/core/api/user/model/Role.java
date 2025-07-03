package com.moonkeyeu.core.api.user.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.moonkeyeu.core.api.user.model.Permissions.*;

@RequiredArgsConstructor
@Getter
public enum Role {
    USER(Collections.emptySet()),
    DEVELOPER(
            Set.of(
                    DEVELOPER_CREATE,
                    DEVELOPER_READ,
                    DEVELOPER_UPDATE,
                    DEVELOPER_DELETE,
                    ADMIN_CREATE,
                    ADMIN_READ,
                    ADMIN_UPDATE,
                    ADMIN_DELETE,
                    MODERATOR_READ,
                    MODERATOR_UPDATE,
                    MODERATOR_DELETE
            )
    ),
    ADMIN(
            Set.of(
                    ADMIN_CREATE,
                    ADMIN_READ,
                    ADMIN_UPDATE,
                    ADMIN_DELETE,
                    MODERATOR_READ,
                    MODERATOR_UPDATE,
                    MODERATOR_DELETE
            )
    ),
    MODERATOR(
            Set.of(
                    MODERATOR_READ,
                    MODERATOR_UPDATE,
                    MODERATOR_DELETE
            )
    );

    private final Set<Permissions> permissions;
    public List<SimpleGrantedAuthority> getAuthorities() {
        return getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());
    }
}
