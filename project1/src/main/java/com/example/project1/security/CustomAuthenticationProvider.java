package com.example.project1.security;

import com.example.project1.domain.User;
import com.example.project1.exception.CommonException;
import com.example.project1.exception.ErrorCode;
import com.example.project1.repository.UserRepository;
import com.example.project1.security.info.JwtUserInfo;
import com.example.project1.security.jwt.BasicToken;
import com.example.project1.security.service.CustomUserDetailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomAuthenticationProvider implements AuthenticationProvider {
    private final CustomUserDetailService customUserDetailService;

    /**
     * 인증 구현
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 전달 받은 UsernamePasswordAuthenticationToken
        JwtUserInfo jwtUserInfo = (JwtUserInfo) authentication.getPrincipal();

        CustomUserDetails userPrincipal = (CustomUserDetails) customUserDetailService.loadUserById(jwtUserInfo.id());

        if (userPrincipal.getRole() != jwtUserInfo.role()) {
            throw new AuthenticationException("Invalid Role") {};
        }

        // 인증 성공 시 UsernamePasswordAuthenticationToken 반환
        return new UsernamePasswordAuthenticationToken(userPrincipal, null, userPrincipal.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
