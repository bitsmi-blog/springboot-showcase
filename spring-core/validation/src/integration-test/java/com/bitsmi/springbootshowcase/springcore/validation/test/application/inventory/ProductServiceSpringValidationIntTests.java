package com.bitsmi.springbootshowcase.springcore.validation.test.application.inventory;

import com.bitsmi.springbootshowcase.springcore.validation.application.config.ApplicationModuleConfig;
import com.bitsmi.springbootshowcase.springcore.validation.application.inventory.ProductService;
import com.bitsmi.springbootshowcase.springcore.validation.application.inventory.dto.CategoryDto;
import com.bitsmi.springbootshowcase.springcore.validation.application.inventory.dto.ProductDto;
import com.bitsmi.springbootshowcase.springcore.validation.util.IgnoreOnComponentScan;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.DisplayName;
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
class ProductServiceSpringValidationIntTests
{
    @Autowired
    private ProductService sut;

    @Test
    @DisplayName("Service should execute given a valid product")
    void validateProductTest1()
    {
        final ProductDto givenProduct = ProductDto.builder()
                .externalId("product-1")
                .name("Product 1")
                .categories(List.of(
                        CategoryDto.builder().externalId("category-1").name("Category 1").build(),
                        CategoryDto.builder().externalId("category-2").name("Category 2").build()
                ))
                .quantity(100)
                .availableSince(LocalDateTime.of(LocalDate.of(2024, 1, 1), LocalTime.MIN))
                .build();

        boolean actualResult = sut.createProduct(givenProduct);

        assertThat(actualResult).isTrue();
    }

    @Test
    @DisplayName("Service should throw a validation error given an invalid product")
    void validateProductTest2()
    {
        final ProductDto givenProduct = ProductDto.builder()
                // Validation fail: Missing externalId
                .name("Product 1")
                .categories(List.of(
                        // Validation fail: Missing name
                        CategoryDto.builder().externalId("category-1").build(),
                        CategoryDto.builder().externalId("category-2").name("Category 2").build()
                ))
                .quantity(100)
                // Validation fail: Future date
                .availableSince(LocalDateTime.now().plusDays(10))
                .build();

        assertThatThrownBy(() -> sut.createProduct(givenProduct))
                .isExactlyInstanceOf(ConstraintViolationException.class)
                .asInstanceOf(InstanceOfAssertFactories.throwable(ConstraintViolationException.class))
                .extracting(ConstraintViolationException::getConstraintViolations)
                .asInstanceOf(InstanceOfAssertFactories.collection(ConstraintViolation.class))
                .extracting(violation -> "%s: %s".formatted(violation.getPropertyPath(), violation.getMessage()))
                .containsExactlyInAnyOrder(
                        "createProduct.productDto.externalId: ExternalId must not be blank",
                        "createProduct.productDto.availableSince: AvailableSince must be a present or past date",
                        "createProduct.productDto.categories[0].name: Name must not be null"
                );
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
