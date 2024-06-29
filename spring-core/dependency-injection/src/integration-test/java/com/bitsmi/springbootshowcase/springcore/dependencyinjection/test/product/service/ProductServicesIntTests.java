package com.bitsmi.springbootshowcase.springcore.dependencyinjection.test.product.service;

import com.bitsmi.springbootshowcase.springcore.dependencyinjection.Constants;
import com.bitsmi.springbootshowcase.springcore.dependencyinjection.product.config.ProductServiceConfig;
import com.bitsmi.springbootshowcase.springcore.dependencyinjection.product.service.ProductService;
import com.bitsmi.springbootshowcase.springcore.dependencyinjection.util.IgnoreOnComponentScan;
import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.InjectSoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.List;
import java.util.Optional;

@ExtendWith({SpringExtension.class, SoftAssertionsExtension.class})
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
@Tag("IntegrationTest")
class ProductServicesIntTests
{
    @Autowired
    private ProductService defaultProductService;
    @Autowired
    @Qualifier(Constants.QUALIFIER_ALTERNATIVE_PRODUCT)
    private ProductService alternativeProductService;
    @Autowired
    private List<ProductService> allProductServicesAsList;
    @Autowired
    private ObjectProvider<ProductService> allProductServicesProvider;

    // Optional service should not be present as OPTIONAL profile is not specified in test
    @Autowired(required = false)
    @Qualifier(Constants.QUALIFIER_OPTIONAL_PRODUCT)
    private ProductService optionalProductService;
    @Autowired
    @Qualifier(Constants.QUALIFIER_OPTIONAL_PRODUCT)
    private ObjectProvider<ProductService> optionalProductServiceProvider;
    @Autowired
    @Qualifier(Constants.QUALIFIER_OPTIONAL_PRODUCT)
    private Optional<ProductService> optOptionalProductService;

    @InjectSoftAssertions
    private SoftAssertions softly;

    @Test
    @DisplayName("should inject the main product service")
    void productServiceInjectionTest1()
    {
        String actualProductName = defaultProductService.getProductName();
        softly.assertThat(actualProductName)
                .as("Actual product name")
                .isEqualTo("Main Product");
    }

    @Test
    @DisplayName("should inject the qualified product service")
    void productServiceInjectionTest2()
    {
        String actualProductName = alternativeProductService.getProductName();
        softly.assertThat(actualProductName)
                .as("Actual product name")
                .isEqualTo("Alternative Product");
    }

    @Test
    @DisplayName("should inject multiple product services when list is used")
    void productServiceInjectionTest3()
    {
        List<String> actualProducts = allProductServicesAsList.stream()
                .map(ProductService::getProductName)
                .toList();

        softly.assertThat(actualProducts)
                .as("Actual product names")
                .hasSize(3)
                .containsExactlyInAnyOrder("Main Product", "Additional Product", "Alternative Product");
    }

    @Test
    @DisplayName("should inject multiple product services when provider is used")
    void productServiceInjectionTest4()
    {
        List<String> actualProducts = allProductServicesProvider.stream()
                .map(ProductService::getProductName)
                .toList();

        softly.assertThat(actualProducts)
                .as("Actual product names")
                .hasSize(3)
                .containsExactlyInAnyOrder("Main Product", "Additional Product", "Alternative Product");
    }

    @Test
    @DisplayName("should not inject optional service given no OPTIONAL profile")
    void productServiceInjectionTest5()
    {
        softly.assertThat(optionalProductService)
                .as("Instance")
                .isNull();
        softly.assertThat(optionalProductServiceProvider.stream())
                .as("Provider")
                .isEmpty();
        softly.assertThat(optOptionalProductService)
                .as("Optional")
                .isEmpty();
    }

    /*---------------------------*
     * TEST CONFIG AND HELPERS
     *---------------------------*/
    @TestConfiguration
    @Import({ ProductServiceConfig.class })
    @IgnoreOnComponentScan
    static class TestConfig
    {

    }
}
