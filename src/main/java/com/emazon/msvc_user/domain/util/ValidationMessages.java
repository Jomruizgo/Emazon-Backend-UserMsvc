package com.emazon.msvc_user.domain.util;

import jakarta.validation.constraints.Size;

public class ValidationMessages {
    private ValidationMessages(){throw new IllegalStateException("Utility class");}
    public static final String NAME_MANDATORY = "Name is mandatory";
    public static final String LAST_NAME_MANDATORY = "Last name is mandatory";
    public static final String DOCUMENT_MANDATORY = "Document is mandatory";
    public static final String DUPLICATE_DOCUMENT = "A user with this document id already exists.";
    public static final String MOBILE_NUMBER_INVALID = "Mobile number must be up to 13 characters: 12 digits and additionally can start with '+'";
    public static final String EMAIL_INVALID = "Email should be valid";
    public static final String DUPLICATE_EMAIL = "A user with this email already exists.";
    public static final String USER_NOT_ADULT = "User must be an adult (18+ years)";
    public static final String ROLE_INVALID = "Role must be valid";
    public static final String PASSWORD_MANDATORY = "Password is mandatory";
    public static final String MIN_PASSWORD_SIZE = "Password must be at least 8 characters long";
}
