package com.bitsmi.springbootshowcase.core.common.util;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidToUpdateValidator implements ConstraintValidator<ValidToUpdate, IUpdateValidationSupport>
{
    @Override
    public boolean isValid(IUpdateValidationSupport value, ConstraintValidatorContext context)
    {
        return validateId(value, context);
    }

    private boolean validateId(IUpdateValidationSupport value, ConstraintValidatorContext context)
    {
        if(value.getId()==null) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("ID not specified")
                    .addConstraintViolation();
            return false;
        }

        return true;
    }
}
