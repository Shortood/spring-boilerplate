package com.example.project1.security.info.factory;

import com.example.project1.domain.type.EProvider;
import com.example.project1.security.info.KakaoOAuth2UserInfo;

import java.util.Map;

public class OAuth2UserInfoFactory {
    public static OAuth2UserInfo getOAuth2UserInfo(EProvider provider, Map<String, Object> attributes) {
        return switch (provider) {
            case KAKAO -> new KakaoOAuth2UserInfo(attributes);
            default -> throw new IllegalArgumentException("Invalid Provider Type.");
        };
    }
}
