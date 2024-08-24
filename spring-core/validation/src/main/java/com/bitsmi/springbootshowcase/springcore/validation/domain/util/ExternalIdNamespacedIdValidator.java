package com.bitsmi.springbootshowcase.springcore.validation.domain.util;

import com.bitsmi.springbootshowcase.springcore.validation.application.inventory.dto.NamespacedId;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.function.Predicate;
import java.util.regex.Pattern;

public class ExternalIdNamespacedIdValidator implements ConstraintValidator<ValidExternalId, NamespacedId> {
    private final Predicate<String> MATCH_PREDICATE = Pattern.compile("^[\\w\\-.]+$").asMatchPredicate();

    @Override
    public boolean isValid(NamespacedId value, ConstraintValidatorContext context)
    {
        return value == null
                || validateString(value.namespace(), context) && validateString(value.id(), context);
    }

    private boolean validateString(String value, ConstraintValidatorContext context)
    {
        return value!=null && MATCH_PREDICATE.test(value);
    }
}
