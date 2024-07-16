package com.example.sontcamp.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    // 개발자가 직접 정의한 예외
    @ExceptionHandler(value = {CommonException.class})
    public ResponseDto<?> handleApiException(CommonException e) {
        //서버, DB 예외가 아니기 때문에 e.printStackTrace X
        log.error("handleApiException() in GlobalExceptionHandler throw CommonException : {}", e.getMessage());
        return ResponseDto.fail(e);
    }

    @ExceptionHandler(value = {HandlerMethodValidationException.class})
    public ResponseDto<?> handleValidationException(HandlerMethodValidationException e) {
        log.error("handleValidationException() in GlobalExceptionHandle throw HandlerMethodValidationException : {}", e.getMessage());
        return ResponseDto.fail(e);
    }

    // 서버, DB 예외
    @ExceptionHandler(value = {Exception.class})
    public ResponseDto<?> handleException(Exception e) {
        log.error("handleException() in GlobalExceptionHandle throw Exception : {}");
        e.printStackTrace();
        return ResponseDto.fail(new CommonException(ErrorCode.SERVER_ERROR));
    }
}
