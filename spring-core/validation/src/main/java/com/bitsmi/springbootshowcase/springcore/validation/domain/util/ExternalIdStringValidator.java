package com.bitsmi.springbootshowcase.springcore.validation.domain.util;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.function.Predicate;
import java.util.regex.Pattern;

public class ExternalIdStringValidator implements ConstraintValidator<ValidExternalId, String>
{
    private final Predicate<String> MATCH_PREDICATE = Pattern.compile("^[\\w\\-.]+$").asMatchPredicate();

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context)
    {
        return validateId(value, context);
    }

    private boolean validateId(String value, ConstraintValidatorContext context)
    {
        return value!=null && MATCH_PREDICATE.test(value);
    }
}
