package com.example.project1.constant;

public class Constants {

    public static final String USER_ID_CLAIM_NAME = "uid";
    public static final String USER_ROLE_CLAIM_NAME = "rol";
    public static final String BEARER_PREFIX = "Bearer ";
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String[] NO_NEED_AUTH_URLS = {
            "/api/auth/basic", "/sign-in", "/api/auth/kakao", "/oauth2/authorization/kakao",
            "/login/oauth2/code/kakao"
    };

}
