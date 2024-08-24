package com.bitsmi.springbootshowcase.springcore.validation.application.util;

import jakarta.validation.GroupSequence;

@GroupSequence({ MandatoryValidationGroup.class, OptionalValidationGroup.class })
public interface FullValidationGroup {

}
