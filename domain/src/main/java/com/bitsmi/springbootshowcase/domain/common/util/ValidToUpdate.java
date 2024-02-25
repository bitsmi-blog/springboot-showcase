package com.bitsmi.springbootshowcase.domain.common.util;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidToUpdateValidator.class)
public @interface ValidToUpdate
{
    String message() default "Invalid updatable record";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
