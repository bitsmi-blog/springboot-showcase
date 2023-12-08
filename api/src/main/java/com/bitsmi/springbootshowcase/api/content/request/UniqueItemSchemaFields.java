package com.bitsmi.springbootshowcase.api.content.request;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = UniqueItemSchemaFieldsValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueItemSchemaFields
{
    String message() default "Invalid item schema fields: {fields}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
