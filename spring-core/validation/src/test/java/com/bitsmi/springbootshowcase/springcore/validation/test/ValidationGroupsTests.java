package com.bitsmi.springbootshowcase.springcore.validation.test;

import com.bitsmi.springbootshowcase.springcore.validation.application.inventory.dto.ProductDto;
import com.bitsmi.springbootshowcase.springcore.validation.application.inventory.dto.StoreDto;
import com.bitsmi.springbootshowcase.springcore.validation.application.util.ValidationGroups;
import com.bitsmi.springbootshowcase.springcore.validation.application.util.ValidationGroups.ValidateAdditionalFields;
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
    @DisplayName("Validation given Default validation group")
    class DefaultValidationGroupTests {
        @Test
        @DisplayName("should success when mandatory fields are provided")
        void defaultGroupValidationGroupTest1()
        {
            final StoreDto providedStore = StoreDto.builder()
                    .externalId("store-1")
                    .name("Store 1")
                    // missing `address`. Not checked by default group
                    .build();

            Set<ConstraintViolation<StoreDto>> constraintViolations = validator.validate(providedStore);

            assertThat(constraintViolations)
                    .isEmpty();
        }

        @Test
        @DisplayName("should fail when mandatory fields are missing")
        void defaultGroupValidationGroupTest2()
        {
            final StoreDto providedStore = StoreDto.builder()
                    .externalId("store-1")
                    // missing `name`
                    .build();

            Set<ConstraintViolation<StoreDto>> constraintViolations = validator.validate(providedStore);

            assertThat(constraintViolations)
                    .extracting(violation -> "%s: %s".formatted(violation.getPropertyPath(), violation.getMessage()))
                    .containsExactlyInAnyOrder(
                            "name: Name must not be null"
                    );
        }
    }

    @Nested
    @DisplayName("Validation given OptionalValidationGroup only")
    class OptionalValidationGroupTests {
        @Test
        @DisplayName("should success when only optional fields are provided")
        void optionalValidationGroupTest1()
        {
            final StoreDto providedStore = StoreDto.builder()
                    .externalId("store-1")
                    // missing `name`. Not checked by `OptionalValidationGroup`
                    .address("Fake street 123")
                    .extraInfo("Extra info text")
                    .build();

            Set<ConstraintViolation<StoreDto>> constraintViolations = validator.validate(providedStore);

            assertThat(constraintViolations)
                    .isEmpty();
        }

        @Test
        @DisplayName("should fail when optional fields are missing")
        void optionalValidationGroupTest2()
        {
            final StoreDto providedStore = StoreDto.builder()
                    .externalId("store-1")
                    // missing `name`. Not checked by `OptionalValidationGroup`
                    // missing optional `address`
                    .extraInfo("Extra info text")
                    .build();

            Set<ConstraintViolation<StoreDto>> constraintViolations = validator.validate(providedStore, ValidationGroups.ValidateOptional.class);

            assertThat(constraintViolations)
                    .extracting(violation -> "%s: %s".formatted(violation.getPropertyPath(), violation.getMessage()))
                    .containsExactlyInAnyOrder(
                            "address: Address must not be null"
                    );
        }
    }

    @Test
    @DisplayName("Validation should fail when mandatory fields are missing given Custom validation group extending default")
    void defaultValidationGroupTest1()
    {
        final ProductDto providedProduct = ProductDto.builder()
                .externalId("product-1")
                // Missing mandatory fields (name)
                .build();

        Set<ConstraintViolation<ProductDto>> constraintViolations = validator.validate(providedProduct, ValidationGroups.ValidateMandatory.class);

        assertThat(constraintViolations)
                .extracting(violation -> "%s: %s".formatted(violation.getPropertyPath(), violation.getMessage()))
                .containsExactlyInAnyOrder(
                        "name: Name must not be null"
                );
    }

    @Test
    @DisplayName("Validation should fail when only additional fields are not provided given AdditionalFieldsValidationGroup validation group ")
    void additionalFieldsValidationGroupTest1()
    {
        final StoreDto providedStore = StoreDto.builder()
                .externalId("store-1")
                // missing `name`. Not checked by `AdditionalFieldsValidationGroup`
                // missing optional `address`
                // missing additional field `extraInfo`
                .build();

        Set<ConstraintViolation<StoreDto>> constraintViolations = validator.validate(providedStore, ValidateAdditionalFields.class);

        assertThat(constraintViolations)
                .extracting(violation -> "%s: %s".formatted(violation.getPropertyPath(), violation.getMessage()))
                .containsExactlyInAnyOrder(
                        "extraInfo: ExtraInfo must not be null"
                );
    }

    @Test
    @DisplayName("Validation should fail when any field are not provided given full validation group ")
    void fullValidationGroupTest1()
    {
        final StoreDto providedStore = StoreDto.builder()
                .externalId("store-1")
                // missing `name`. Not checked by `AdditionalFieldsValidationGroup`
                // missing optional `address`
                // missing additional field `extraInfo`
                .build();

        Set<ConstraintViolation<StoreDto>> constraintViolations = validator.validate(providedStore, ValidationGroups.FullValidation.class);

        /* Includes validations for mandatory, optional and additional fields but validation is fail fast when validating groups in the specified order
         * so it will report only errors for mandatory group
         */
        assertThat(constraintViolations)
                .extracting(violation -> "%s: %s".formatted(violation.getPropertyPath(), violation.getMessage()))
                .containsExactlyInAnyOrder(
                        "name: Name must not be null"
                );
    }
}