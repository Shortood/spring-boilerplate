package com.example.sontcamp.intercepter;

import com.example.sontcamp.annotation.UserId;
import com.example.sontcamp.exception.CommonException;
import com.example.sontcamp.exception.ErrorCode;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class UserIdArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(Long.class) //파라미터가 Long 타입이고
                && parameter.hasParameterAnnotation(UserId.class); //UserId 어노테이션일 때만 resolver 적용
    }

    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) throws Exception {
        //USER_ID 라는 속성을 가져옴 → Long 타입 변환
        final Object userIdObj = webRequest.getAttribute("USER_ID", WebRequest.SCOPE_REQUEST);
        
        //없으면 예외처리
        if (userIdObj == null) {
            throw new CommonException(ErrorCode.ACCESS_DENIED_ERROR);
        }
        //Long 타입으로 변환해 반환
        return Long.valueOf(userIdObj.toString());
    }
}