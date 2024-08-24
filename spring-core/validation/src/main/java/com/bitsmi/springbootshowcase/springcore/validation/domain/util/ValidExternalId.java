package com.bitsmi.springbootshowcase.springcore.validation.domain.util;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy =  { ExternalIdStringValidator.class, ExternalIdNamespacedIdValidator.class })
public @interface ValidExternalId
{
    String message() default "Invalid External Id";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
