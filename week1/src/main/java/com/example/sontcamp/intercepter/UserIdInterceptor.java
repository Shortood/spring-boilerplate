package com.example.sontcamp.intercepter;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;

public class UserIdInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //request.setAttribute("USER_ID", authentication.getName());

        //시큐리티를 제대로 적용하지 않아 임시로 무조건 1번 아이디로 속성 적용하도록 함
        request.setAttribute("USER_ID", 1L);
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}