package com.example.sontcamp.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    // Not Found Error
    NOT_FOUND_USER("4040", HttpStatus.NOT_FOUND, "해당 사용자가 존재하지 않습니다."),

    // Server, File Up/DownLoad Error
    SERVER_ERROR("5000", HttpStatus.INTERNAL_SERVER_ERROR, "API 서버 오류입니다."),

    // Access Denied Error
    ACCESS_DENIED_ERROR("4030", HttpStatus.FORBIDDEN, "액세스 권한이 없습니다."),

    // Bad Request Error
    INVALID_PARAMETER("4050", HttpStatus.BAD_REQUEST, "Invalid PARAMETER");

    private final String code;
    private final HttpStatus httpStatus;
    private final String message;
}
