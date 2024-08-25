package com.bitsmi.springbootshowcase.springcore.validation.test;

import com.bitsmi.springbootshowcase.springcore.validation.application.inventory.dto.ProductDto;
import com.bitsmi.springbootshowcase.springcore.validation.application.inventory.dto.StoreDto;
import com.bitsmi.springbootshowcase.springcore.validation.application.util.AdditionalFieldsValidationGroup;
import com.bitsmi.springbootshowcase.springcore.validation.application.util.MandatoryValidationGroup;
import com.bitsmi.springbootshowcase.springcore.validation.application.util.OptionalValidationGroup;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class ValidationGroupsTests {

    private Validator validator;

    @BeforeEach
    void setupUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Nested
    @DisplayName("Validation given MandatoryValidationGroup")
    class MandatoryValidationGroupTests {
        @Test
        @DisplayName("should success when mandatory fields are provided")
        void mandatoryValidationGroupTest1()
        {
            final StoreDto providedStore = StoreDto.builder()
                    .externalId("store-1")
                    .name("Store 1")
                    // missing `address`. Not checked by `MandatoryValidationGroup`
                    .build();

            Set<ConstraintViolation<StoreDto>> constraintViolations = validator.validate(providedStore, MandatoryValidationGroup.class);

            assertThat(constraintViolations)
                    .isEmpty();
        }

        @Test
        @DisplayName("should fail when mandatory fields are missing")
        void mandatoryValidationGroupTest2()
        {
            final StoreDto providedStore = StoreDto.builder()
                    .externalId("store-1")
                    // missing `name`
                    .build();

            Set<ConstraintViolation<StoreDto>> constraintViolations = validator.validate(providedStore, MandatoryValidationGroup.class);

            assertThat(constraintViolations)
                    .extracting(violation -> "%s: %s".formatted(violation.getPropertyPath(), violation.getMessage()))
                    .containsExactlyInAnyOrder(
                            "name: Name must not be null"
                    );
        }
    }

    @Nested
    @DisplayName("Validation given OptionalValidationGroup")
    class OptionalValidationGroupTests {
        @Test
        @DisplayName("should success when optional fields are provided")
        void optionalValidationGroupTest1()
        {
            final StoreDto providedStore = StoreDto.builder()
                    .externalId("store-1")
                    // missing `name`. Not checked by `AdditionalFieldsValidationGroup`
                    .address("Fake street 123")
                    .build();

            Set<ConstraintViolation<StoreDto>> constraintViolations = validator.validate(providedStore, OptionalValidationGroup.class);

            assertThat(constraintViolations)
                    .isEmpty();
        }

        @Test
        @DisplayName("should fail when optional fields are missing")
        void optionalValidationGroupTest2()
        {
            final StoreDto providedStore = StoreDto.builder()
                    .externalId("store-1")
                    // missing `name`. Not checked by `AdditionalFieldsValidationGroup`
                    // missing optional `address`
                    .build();

            Set<ConstraintViolation<StoreDto>> constraintViolations = validator.validate(providedStore, OptionalValidationGroup.class);

            assertThat(constraintViolations)
                    .extracting(violation -> "%s: %s".formatted(violation.getPropertyPath(), violation.getMessage()))
                    .containsExactlyInAnyOrder(
                            "address: Address must not be null"
                    );
        }
    }

    @Test
    @DisplayName("Validation should success when mandatory and optional fields are missing given Default validation group")
    void defaultValidationGroupTest1()
    {
        final StoreDto providedStore = StoreDto.builder()
                .externalId("store-1")
                // missing `name`
                // missing optional `address`
                .build();

        Set<ConstraintViolation<StoreDto>> constraintViolations = validator.validate(providedStore);

        assertThat(constraintViolations)
                .isEmpty();
    }

    @Test
    @DisplayName("Validation should success when mandatory and optional fields are missing given Custom validation group")
    void defaultValidationGroupTest2()
    {
        final ProductDto providedProduct = ProductDto.builder()
                .externalId("product-1")
                // Missing mandatory fields
                .build();

        Set<ConstraintViolation<ProductDto>> constraintViolations = validator.validate(providedProduct, MandatoryValidationGroup.class);

        assertThat(constraintViolations)
                .isEmpty();
    }

    @Test
    @DisplayName("Validation should fail when only additional fields are not provided given AdditionalFieldsValidationGroup validation group ")
    void additionalFieldsValidationGroupTest1()
    {
        final StoreDto providedStore = StoreDto.builder()
                .externalId("store-1")
                // missing `name`. Not checked by `AdditionalFieldsValidationGroup`
                // missing optional `address`
                .build();

        Set<ConstraintViolation<StoreDto>> constraintViolations = validator.validate(providedStore, AdditionalFieldsValidationGroup.class);

        assertThat(constraintViolations)
                .extracting(violation -> "%s: %s".formatted(violation.getPropertyPath(), violation.getMessage()))
                .containsExactlyInAnyOrder(
                        "address: Address must not be null"
                );
    }
}