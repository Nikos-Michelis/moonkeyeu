package com.moonkeyeu.core.api.user.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.moonkeyeu.core.api.launch.dto.DTOEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Set;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO implements DTOEntity {
    @JsonProperty("id")
    private Long userId;
    @JsonProperty("username")
    private String username;
    @JsonProperty("email")
    private String email;
    @JsonProperty("accountNonLocked")
    private boolean accountNonLocked;
    @JsonProperty("accountNonExpired")
    private boolean accountNonExpired;
    @JsonProperty("credentialsNonExpired")
    private boolean credentialsNonExpired;
    @JsonProperty("enabled")
    private boolean enabled;
    @JsonProperty("credentialsExpiryDate")
    private Instant credentialsExpiryDate;
    @JsonProperty("accountExpiryDate")
    private Instant accountExpiryDate;
    @JsonProperty("role")
    private Set<String> role;
    @JsonProperty("createdAt")
    private Instant createdAt;
    @JsonProperty("updatedAt")
    private Instant updatedAt;
}
