package com.bitsmi.springbootshowcase.springcore.validation.domain.util;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = UniqueValuesValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueValues
{
    String NOT_DEFINED_VALUE = "__ND__";

    String message() default "Values must be unique: {values}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    String field() default NOT_DEFINED_VALUE;
}
