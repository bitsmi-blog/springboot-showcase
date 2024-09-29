package com.bitsmi.springbootshowcase.springcore.validation.application.util;

import jakarta.validation.GroupSequence;
import jakarta.validation.groups.Default;

public final class ValidationGroups {

    private ValidationGroups() { }

    public interface ValidateMandatory extends Default { }
    public interface ValidateOptional { }
    public interface ValidateAdditionalFields { }

    @GroupSequence({ ValidateMandatory.class, ValidateOptional.class, ValidateAdditionalFields.class })
    public interface FullValidation { }
}
