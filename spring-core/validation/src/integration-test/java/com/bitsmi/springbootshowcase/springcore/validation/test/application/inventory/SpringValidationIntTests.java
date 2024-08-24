package com.bitsmi.springbootshowcase.springcore.validation.test.application.inventory;

import com.bitsmi.springbootshowcase.springcore.validation.application.config.ApplicationModuleConfig;
import com.bitsmi.springbootshowcase.springcore.validation.application.inventory.InventoryService;
import com.bitsmi.springbootshowcase.springcore.validation.application.inventory.dto.CategoryDto;
import com.bitsmi.springbootshowcase.springcore.validation.application.inventory.dto.ProductDto;
import com.bitsmi.springbootshowcase.springcore.validation.application.inventory.dto.StoreDto;
import com.bitsmi.springbootshowcase.utils.IgnoreOnComponentScan;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith({ SpringExtension.class })
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
@Tag("IntegrationTest")
class SpringValidationIntTests
{
    @Autowired
    private InventoryService inventoryService;

    @Nested
    @DisplayName("Parameter default validation")
    class DefaultGroupValidationTests {
        @Test
        @DisplayName("Should succeed when a valid product is provided")
        void successTest1() {
            final ProductDto providedProduct = ProductDto.builder()
                    .externalId("product-1")
                    .name("Product 1")
                    .categories(List.of(
                            CategoryDto.builder().externalId("category-1").name("Category 1").build(),
                            CategoryDto.builder().externalId("category-2").name("Category 2").build()
                    ))
                    .quantity(100)
                    .availableSince(LocalDateTime.of(LocalDate.of(2024, 1, 1), LocalTime.MIN))
                    .build();

            boolean actualResult = inventoryService.createProduct(providedProduct);

            assertThat(actualResult).isTrue();
        }

        @Test
        @DisplayName("Should fail when an invalid product is provided")
        void failTest1()
        {
            final ProductDto providedProduct = ProductDto.builder()
                    // Validation fail: Missing externalId
                    .name("Product 1")
                    .categories(List.of(
                            // Validation fail: Missing name
                            CategoryDto.builder().externalId("category-1").build(),
                            CategoryDto.builder().externalId("category-2").name("Category 2").build(),
                            // Validation fail: Non unique
                            CategoryDto.builder().externalId("category-2").name("Category 2").build()
                    ))
                    .quantity(100)
                    // Validation fail: Future date
                    .availableSince(LocalDateTime.now().plusDays(10))
                    .build();

            assertThatThrownBy(() -> inventoryService.createProduct(providedProduct))
                    .isExactlyInstanceOf(ConstraintViolationException.class)
                    .asInstanceOf(InstanceOfAssertFactories.throwable(ConstraintViolationException.class))
                    .extracting(ConstraintViolationException::getConstraintViolations)
                    .asInstanceOf(InstanceOfAssertFactories.collection(ConstraintViolation.class))
                    .extracting(violation -> "%s: %s".formatted(violation.getPropertyPath(), violation.getMessage()))
                    .containsExactlyInAnyOrder(
                            "createProduct.productDto.externalId: ExternalId must not be blank",
                            "createProduct.productDto.availableSince: AvailableSince must be a present or past date",
                            "createProduct.productDto.categories[0].name: Name must not be null",
                            "createProduct.productDto.categories: Values must be unique: category-2"
                    );
        }
    }

    @Nested
    @DisplayName("""
            Parameter group validation
             Given MandatoryValidationGroup validation
            """
    )
    class MandatoryGroupValidationTests {
        @Test
        @DisplayName("Should success when only mandatory fields are provided")
        void mandatoryValidationGroupTest1()
        {
            final StoreDto providedStore = StoreDto.builder()
                    .externalId("store-1")
                    .name("Store 1")
                    // missing optional `address`
                    .build();

            boolean actualResult = inventoryService.createStoreWithMandatoryData(providedStore);

            assertThat(actualResult).isTrue();
        }

        @Test
        @DisplayName("Should fail when only mandatory fields are missing")
        void mandatoryValidationGroupTest2()
        {
            final StoreDto providedStore = StoreDto.builder()
                    .externalId("store-1")
                    // missing mandatory `name`
                    .build();

            assertThatThrownBy(() -> inventoryService.createStoreWithMandatoryData(providedStore))
                    .isExactlyInstanceOf(ConstraintViolationException.class)
                    .asInstanceOf(InstanceOfAssertFactories.throwable(ConstraintViolationException.class))
                    .extracting(ConstraintViolationException::getConstraintViolations)
                    .asInstanceOf(InstanceOfAssertFactories.collection(ConstraintViolation.class))
                    .extracting(violation -> "%s: %s".formatted(violation.getPropertyPath(), violation.getMessage()))
                    .containsExactlyInAnyOrder(
                            "createStoreWithMandatoryData.storeDto.name: Name must not be null"
                    );
        }
    }

    @Nested
    @DisplayName("""
            Parameter group validation
             Given FullValidationGroup validation
            """
    )
    class FullGroupValidationTests {
        @Test
        @DisplayName("Should fail when a mandatory field is missing")
        void fullValidationGroupTest1()
        {
            final StoreDto providedStore = StoreDto.builder()
                    .externalId("store-1")
                    // missing mandatory `name`
                    // missing optional `address`
                    .build();

            /* Validation groups are processed separately. If first group finds a constraint violation,
             * validation will be stopped and only the failures from this group will be reported
             */
            assertThatThrownBy(() -> inventoryService.createStoreWithFullData(providedStore))
                    .isExactlyInstanceOf(ConstraintViolationException.class)
                    .asInstanceOf(InstanceOfAssertFactories.throwable(ConstraintViolationException.class))
                    .extracting(ConstraintViolationException::getConstraintViolations)
                    .asInstanceOf(InstanceOfAssertFactories.collection(ConstraintViolation.class))
                    .extracting(violation -> "%s: %s".formatted(violation.getPropertyPath(), violation.getMessage()))
                    .containsExactlyInAnyOrder(
                            "createStoreWithFullData.storeDto.name: Name must not be null"
                    );
        }

        @Test
        @DisplayName("Should fail when an optional field is missing")
        void validateStoreFullDataTest2()
        {
            final StoreDto providedStore = StoreDto.builder()
                    .externalId("store-1")
                    .name("Store 1")
                    // missing optional `address`
                    .build();

            assertThatThrownBy(() -> inventoryService.createStoreWithFullData(providedStore))
                    .isExactlyInstanceOf(ConstraintViolationException.class)
                    .asInstanceOf(InstanceOfAssertFactories.throwable(ConstraintViolationException.class))
                    .extracting(ConstraintViolationException::getConstraintViolations)
                    .asInstanceOf(InstanceOfAssertFactories.collection(ConstraintViolation.class))
                    .extracting(violation -> "%s: %s".formatted(violation.getPropertyPath(), violation.getMessage()))
                    .containsExactlyInAnyOrder(
                            "createStoreWithFullData.storeDto.address: Address must not be null"
                    );
        }
    }

    /*---------------------------*
     * TEST CONFIG AND HELPERS
     *---------------------------*/
    @TestConfiguration
    @Import({ ApplicationModuleConfig.class })
    @EnableAutoConfiguration
    @IgnoreOnComponentScan
    static class TestConfig
    {

    }
}
