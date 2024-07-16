package com.example.project1.security.config;

import com.example.project1.constant.Constants;
import com.example.project1.security.BasicAuthenticationProvider;
import com.example.project1.security.CustomAuthenticationProvider;
import com.example.project1.security.service.CustomOAuth2UserService;
import com.example.project1.security.service.CustomUserDetailService;
import com.example.project1.security.filter.JwtExceptionFilter;
import com.example.project1.security.filter.JwtFilter;
import com.example.project1.security.handler.*;
import com.example.project1.security.JwtAuthEntryPoint;
import com.example.project1.security.handler.signin.DefaultSignInFailureHandler;
import com.example.project1.security.handler.signin.DefaultSignInSuccessHandler;
import com.example.project1.security.handler.signin.OAuth2LoginFailureHandler;
import com.example.project1.security.handler.signin.OAuth2LoginSuccessHandler;
import com.example.project1.security.handler.signout.CustomSignOutProcessHandler;
import com.example.project1.security.handler.signout.CustomSignOutResultHandler;
import com.example.project1.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final DefaultSignInSuccessHandler defaultSignInSuccessHandler;
    private final DefaultSignInFailureHandler defaultSignInFailureHandler;
    private final JwtAuthEntryPoint jwtAuthEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final JwtUtil jwtUtil;
    private final CustomUserDetailService customUserDetailService;
    private final CustomSignOutProcessHandler customSignOutProcessHandler;
    private final CustomSignOutResultHandler customSignOutResultHandler;
    private final OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;
    private final OAuth2LoginFailureHandler oAuth2LoginFailureHandler;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final CustomAuthenticationProvider customAuthenticationProvider;
    private final BasicAuthenticationProvider basicAuthenticationProvider;


    @Bean
    protected SecurityFilterChain securityFilterChain(final HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable) //보호 비활성화
                .httpBasic(AbstractHttpConfigurer::disable) // 기본 HTTP 기본 인증 비활성화
                .sessionManagement((sessionManagement) -> //상태를 유지하지 않는 세션 정책 설정
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(requestMatcherRegistry -> requestMatcherRegistry
                        .requestMatchers(Constants.NO_NEED_AUTH_URLS).permitAll()
                        .anyRequest().authenticated())
                //폼 기반 로그인 설정
                .formLogin(configurer ->
                        configurer
                                .loginPage("/login")
                                .loginProcessingUrl("/sign-in") //로그인 처리 URL (POST)
                                .usernameParameter("serialId") //사용자 아이디 파라미터 이름
                                .passwordParameter("password") //비밀번호 파라미터 이름
                                .successHandler(defaultSignInSuccessHandler) //로그인 성공 핸들러
                                .failureHandler(defaultSignInFailureHandler) // 로그인 실패 핸들러
                )//.userDetailsService(customUserDetailService) //사용자 검색할 서비스 설정
                //소셜 로그인
                .oauth2Login(configurer ->
                        configurer
                                .successHandler(oAuth2LoginSuccessHandler)
                                .failureHandler(oAuth2LoginFailureHandler)
                                .userInfoEndpoint(userInfoEndpoint ->
                                        userInfoEndpoint.userService(customOAuth2UserService)
                                )
                )
                // 로그아웃 설정
                .logout(configurer ->
                        configurer
                                .logoutUrl("/auth/logout")
                                .addLogoutHandler(customSignOutProcessHandler)
                                .logoutSuccessHandler(customSignOutResultHandler)
                                .deleteCookies("JSESSIONID")
                )

                //예외 처리 설정
                .exceptionHandling(configurer ->
                        configurer
                                .authenticationEntryPoint(jwtAuthEntryPoint)
                                .accessDeniedHandler(jwtAccessDeniedHandler)
                )
                .addFilterBefore(new JwtFilter(jwtUtil, customAuthenticationProvider), LogoutFilter.class)
                .addFilterBefore(new JwtExceptionFilter(), JwtFilter.class)

                //SecurityFilterChain 빈을 반환
                .getOrBuild();
    }
}
