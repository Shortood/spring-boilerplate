package com.example.project1.dto;

import com.example.project1.exception.CommonException;
import com.example.project1.exception.ErrorCode;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.util.Set;
// 사용 X
public abstract class SelfValidating<T> {
    private final Validator validator;


    public SelfValidating() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    protected void validateSelf() {
        Set<ConstraintViolation<T>> violations = validator.validate((T) this);
        if (!violations.isEmpty()) {
            throw new CommonException(ErrorCode.BAD_DATA);
        }
    }
}
