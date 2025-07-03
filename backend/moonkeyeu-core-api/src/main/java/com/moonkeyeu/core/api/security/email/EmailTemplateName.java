package com.moonkeyeu.core.api.security.email;

import lombok.Getter;

@Getter
public enum EmailTemplateName {
    ACTIVATE_ACCOUNT("activate_account"),
    VERIFY_ACCOUNT("verify_account"),
    RESET_PASSWORD("reset_password");

    private final String name;
    EmailTemplateName(String name){
        this.name = name;
    }

}
