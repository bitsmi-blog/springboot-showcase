package com.bitsmi.springbootshowcase.springcore.validation.test;

import com.bitsmi.springbootshowcase.springcore.validation.application.inventory.dto.NamespacedElementDto;
import com.bitsmi.springbootshowcase.springcore.validation.application.inventory.dto.NamespacedId;
import com.bitsmi.springbootshowcase.springcore.validation.application.inventory.dto.SimpleElementDto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class CustomValidatorTests {

    private Validator validator;

    @BeforeEach
    void setupUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    @DisplayName("Validation should fail when namespaced id with invalid format is provided")
    void multipleValidatorsTest1() {
        NamespacedElementDto providedElement = NamespacedElementDto.builder()
                .externalId(new NamespacedId("namespace", "invalid id format"))
                .name("Element 1")
                .build();

        Set<ConstraintViolation<NamespacedElementDto>> constraintViolations = validator.validate(providedElement);

        assertThat(constraintViolations)
                .extracting(violation -> "%s: %s".formatted(violation.getPropertyPath(), violation.getMessage()))
                .containsExactlyInAnyOrder(
                        "externalId: Invalid ExternalId"
                );
    }

    @Test
    @DisplayName("Validation should fail when id with invalid format is provided")
    void multipleValidatorsTest2() {
        SimpleElementDto providedElement = SimpleElementDto.builder()
                .externalId("invalid id format")
                .name("Element 1")
                .build();

        Set<ConstraintViolation<SimpleElementDto>> constraintViolations = validator.validate(providedElement);

        assertThat(constraintViolations)
                .extracting(violation -> "%s: %s".formatted(violation.getPropertyPath(), violation.getMessage()))
                .containsExactlyInAnyOrder(
                        "externalId: Invalid ExternalId"
                );
    }
}
