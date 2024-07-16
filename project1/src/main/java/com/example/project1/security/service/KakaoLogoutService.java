package com.example.project1.security.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class KakaoLogoutService {

    private static final String KAKAO_LOGOUT_URL = "https://kapi.kakao.com/v1/user/logout";

    public void logoutFromKakao(String accessToken) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);

        HttpEntity<String> request = new HttpEntity<>(headers);
        restTemplate.postForObject(KAKAO_LOGOUT_URL, request, String.class);
    }
}
