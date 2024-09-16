package com.emazon.msvc_user.domain.util;

public enum TestUserRole {
    ADMIN("ADMINISTRATOR"),
    CLIENT("CLIENT"),
    WAREHOUSE("WAREHOUSE_ASSISTANT");

    private final String roleName;

    TestUserRole(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }
}
