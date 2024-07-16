package com.example.project1.annotation;

import com.example.project1.validator.LocalDateValidator;
import jakarta.validation.Constraint;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface UserId {
}
