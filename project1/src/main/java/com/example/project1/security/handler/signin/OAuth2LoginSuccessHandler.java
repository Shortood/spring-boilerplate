package com.example.project1.security.handler.signin;

import com.example.project1.domain.User;
import com.example.project1.domain.type.EProvider;
import com.example.project1.domain.type.ERole;
import com.example.project1.dto.response.JwtTokenDto;
import com.example.project1.repository.UserRepository;
import com.example.project1.security.CustomUserDetails;
import com.example.project1.security.info.factory.OAuth2UserInfo;
import com.example.project1.security.info.factory.OAuth2UserInfoFactory;
import com.example.project1.util.CookieUtil;
import com.example.project1.util.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        CustomUserDetails userPrincipal = (CustomUserDetails) authentication.getPrincipal();

        JwtTokenDto jwtTokenDto = jwtUtil.generateTokens(userPrincipal.getId(), userPrincipal.getRole());
        userRepository.updateRefreshTokenAndLoginStatus(userPrincipal.getId(), jwtTokenDto.getRefreshToken(), true);

        CookieUtil.addSecureCookie(response, "refreshToken", jwtTokenDto.getRefreshToken(), jwtUtil.getWebRefreshTokenExpirationSecond());
        CookieUtil.addCookie(response, "accessToken", jwtTokenDto.getAccessToken());

        if (userPrincipal.getRole() == ERole.GUEST) {
            response.sendRedirect("http://localhost:5173/sign-up");
        } else {
            response.sendRedirect("http://localhost:5173");
        }
    }
}
