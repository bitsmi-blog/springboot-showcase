package com.bitsmi.springbootshowcase.springcore.dependencyinjection.test.product.service;

import com.bitsmi.springbootshowcase.springcore.dependencyinjection.Constants;
import com.bitsmi.springbootshowcase.springcore.dependencyinjection.product.config.ProductServiceConfig;
import com.bitsmi.springbootshowcase.springcore.dependencyinjection.product.service.ProductService;
import com.bitsmi.springbootshowcase.utils.IgnoreOnComponentScan;
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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.List;
import java.util.Optional;

@ExtendWith({SpringExtension.class, SoftAssertionsExtension.class})
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
@ActiveProfiles({ Constants.PROFILE_OPTIONAL })
@Tag("IntegrationTest")
class ProfileServicesOptionalProfileIntTests
{
    @Autowired
    private List<ProductService> allProductServices;

    // Optional service should be present as OPTIONAL profile is specified in test
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
    @DisplayName("should inject multiple product services when list is used")
    void productServiceInjectionTest1()
    {
        List<String> actualProducts = allProductServices.stream()
                .map(ProductService::getProductName)
                .toList();

        softly.assertThat(actualProducts)
                .as("Actual product names")
                .hasSize(4)
                .containsExactlyInAnyOrder("Main Product", "Additional Product", "Alternative Product", "Optional Product");
    }

    @Test
    @DisplayName("should inject the qualified product service")
    void productServiceInjectionTest2()
    {
        String actualProductName = optionalProductService.getProductName();
        softly.assertThat(actualProductName)
                .as("Actual product name")
                .isEqualTo("Optional Product");
    }

    @Test
    @DisplayName("should inject optional service given OPTIONAL profile")
    void productServiceInjectionTest5()
    {
        softly.assertThat(optionalProductService)
                .as("Instance")
                .isNotNull();
        softly.assertThat(optionalProductServiceProvider.stream())
                .as("Provider")
                .hasSize(1);
        softly.assertThat(optOptionalProductService)
                .as("Optional")
                .isPresent();
    }

    @Test
    @DisplayName("optionalProductServiceProvider should return same instance when call multiple times")
    void test()
    {
        ProductService actualInstance1 = optionalProductServiceProvider.getObject();
        ProductService actualInstance2 = optionalProductServiceProvider.getObject();

        softly.assertThat(actualInstance1).isNotNull();
        softly.assertThat(actualInstance2)
                .isNotNull()
                .isSameAs(actualInstance1);
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
