package com.bitsmi.springbootshowcase.sampleapps.domain.common.util;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidToUpdateValidator implements ConstraintValidator<ValidToUpdate, UpdateValidationSupport>
{
    @Override
    public boolean isValid(UpdateValidationSupport value, ConstraintValidatorContext context)
    {
        return validateId(value, context);
    }

    private boolean validateId(UpdateValidationSupport value, ConstraintValidatorContext context)
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
