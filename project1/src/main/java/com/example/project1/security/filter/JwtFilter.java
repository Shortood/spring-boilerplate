package com.example.project1.security.filter;

import com.example.project1.constant.Constants;
import com.example.project1.domain.type.ERole;
import com.example.project1.exception.CommonException;
import com.example.project1.exception.ErrorCode;
import com.example.project1.security.CustomAuthenticationProvider;
import com.example.project1.security.info.JwtUserInfo;
import com.example.project1.security.info.factory.OAuth2UserInfo;
import com.example.project1.security.service.CustomUserDetailService;
import com.example.project1.security.CustomUserDetails;
import com.example.project1.util.HeaderUtil;
import com.example.project1.util.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

//이거 소셜
@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final CustomAuthenticationProvider customAuthenticationProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        // Request Header에서 토큰 추출
        String token = HeaderUtil.refineHeader(request, Constants.AUTHORIZATION_HEADER, Constants.BEARER_PREFIX)
                .orElseThrow(() -> new CommonException(ErrorCode.INVALID_HEADER));

        //토근 검증
        Claims claims = jwtUtil.validateToken(token);

        JwtUserInfo userInfo = new JwtUserInfo(
                Long.valueOf(claims.get(Constants.USER_ID_CLAIM_NAME, String.class)),
                ERole.valueOf(claims.get(Constants.USER_ROLE_CLAIM_NAME, String.class))
        );

        // 인증 전 객체
        UsernamePasswordAuthenticationToken beforeAuthentication = new UsernamePasswordAuthenticationToken(
                userInfo,
                null,
                null
        );

        //인증 후 객체 생성
        UsernamePasswordAuthenticationToken afterAuthentication =
                (UsernamePasswordAuthenticationToken) customAuthenticationProvider.authenticate(beforeAuthentication);

        //인증 후 객체에 요정 정보 추가
        afterAuthentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        //객체 넣어주기
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(afterAuthentication);
        SecurityContextHolder.setContext(context);

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        return Arrays.stream(Constants.NO_NEED_AUTH_URLS).anyMatch(path::startsWith);
    }

}
