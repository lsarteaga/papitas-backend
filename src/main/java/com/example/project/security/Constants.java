package com.example.project.security;

public class Constants {
    // Spring Security

    public static final String LOGIN_URL = "/login";
    public static final String HEADER_AUTHORIZATION_KEY = "Authorization";
    public static final String TOKEN_BEARER_PREFIX = "Bearer";

    // JWT

    public static final String ISSUER_INFO = "https://www.autentia.com/";
    public static final String SUPER_SECRET_KEY = "my_secret_token";
    public static final long TOKEN_EXPIRATION_TIME = 864_000_000; // 10 day
    public static final String AUTHORITIES_KEY = "CLAIM_TOKEN";
    public static final String ISSUER_TOKEN = "ISSUER";
    public static final int ACCESS_TOKEN_VALIDITY_SECONDS = 28800;
}
