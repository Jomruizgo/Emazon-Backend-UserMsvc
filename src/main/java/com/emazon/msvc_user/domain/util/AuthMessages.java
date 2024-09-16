package com.emazon.msvc_user.domain.util;

public class AuthMessages {
    private AuthMessages(){throw new IllegalStateException("Utility class");}


    public static final String INVALID_CREDENTIALS_MESSAGE = "Invalid credentials";

    public static final String BAD_CREDENTIALS_MESSAGE = "Bad credentials";

    public static final String NOT_ACTIVE_MESSAGE = "Disabled account. Please, contact your administrator";

    public static final String INVALID_TOKEN_MESSAGE = "Token invalid, not Authorized";
}
