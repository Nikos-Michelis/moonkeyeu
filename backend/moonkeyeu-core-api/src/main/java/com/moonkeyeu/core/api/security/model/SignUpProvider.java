package com.moonkeyeu.core.api.security.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum SignUpProvider {
    Google("google"),
    Github("github"),
    Facebook("facebook"),

    Password("password");

    @Getter
    private final String providers;
}
