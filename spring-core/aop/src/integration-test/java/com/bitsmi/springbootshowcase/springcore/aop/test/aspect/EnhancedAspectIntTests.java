package com.bitsmi.springbootshowcase.springcore.aop.test.aspect;

import com.bitsmi.springbootshowcase.springcore.aop.AopModulePackage;
import com.bitsmi.springbootshowcase.springcore.aop.aspect.StringEnhancer;
import com.bitsmi.springbootshowcase.springcore.aop.service.ProductService;
import com.bitsmi.springbootshowcase.utils.IgnoreOnComponentScan;
import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.InjectSoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

@ExtendWith({SpringExtension.class, SoftAssertionsExtension.class})
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
@Tag("IntegrationTest")
class EnhancedAspectIntTests {

    @Autowired
    private ProductService productService;

    @SpyBean
    private StringEnhancer stringEnhancerSpy;

    @InjectSoftAssertions
    private SoftAssertions softly;

    @Test
    @DisplayName("aspect should be executed given an enhanced method")
    void enhancedMethodTest1() {
        String actualProductName = productService.getProductName();

        softly.assertThat(actualProductName)
                .as("Product name")
                .isEqualTo("Enhanced Main Product");

        verify(stringEnhancerSpy).enhance("Main Product");
    }

    @Test
    @DisplayName("aspect should not be executed given a not enhanced method")
    void notEnhancedMethodTest1() {
        String actualProductName = productService.getProductReference();

        softly.assertThat(actualProductName)
                .as("Product reference")
                .isEqualTo("ref-main");

        verifyNoInteractions(stringEnhancerSpy);
    }

    /*---------------------------*
     * TEST CONFIG AND HELPERS
     *---------------------------*/
    @TestConfiguration
    @ComponentScan(
            basePackageClasses = { AopModulePackage.class },
            excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = IgnoreOnComponentScan.class)
    )
    // Enable autoconfiguration of AOP starter
    @EnableAutoConfiguration
    static class TestConfig {

    }
}
