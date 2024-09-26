package com.emazon.msvc_user.domain.util;

public class Constants {
    private Constants(){throw new IllegalStateException("Utility class");}

    public static final int MAX_MOBILE_NUMBER_LENGTH = 13;

    public static final String API_AUTH_PATH = "api/auth";
    public static final String LOGIN_SEMI_PATH = "/login";


    public static final String API_USER_PATH = "api/user";
    public static final String WAREHOUSE_SEMI_PATH = "/warehouse";

    //User Details Constants
    public static final boolean DEFAULT_ACCOUNT_NON_EXPIRED = true;
    public static final boolean DEFAULT_CREDENTIAL_IS_NON_EXPIRED = true;
    public static final boolean DEFAULT_ACCOUNT_NON_LOCKED = true;

}
