package com.example.sontcamp.exception;

import com.example.sontcamp.dto.ExceptionDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.micrometer.common.lang.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.springframework.http.HttpStatus;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

@Builder
public record ResponseDto<T>(@JsonIgnore HttpStatus httpStatus,
                             @NotNull Boolean success,
                             @Nullable T data,
                             @Nullable ExceptionDto error) {

    public static <T> ResponseDto<T> ok(@Nullable T data) { //성공한 경우
        return new ResponseDto<T>(HttpStatus.OK, true, data, null);
    }

    public static ResponseDto<Object> fail(final HandlerMethodValidationException e) { //실패한 경우
        return new ResponseDto<>(HttpStatus.BAD_REQUEST, false, null, new ExceptionDto(ErrorCode.INVALID_PARAMETER));
    }

    public static ResponseDto<Object> fail(final CommonException e) { //실패한 경우
        return new ResponseDto<>(e.getErrorCode().getHttpStatus(), false, null, new ExceptionDto(e.getErrorCode()));
    }
}
