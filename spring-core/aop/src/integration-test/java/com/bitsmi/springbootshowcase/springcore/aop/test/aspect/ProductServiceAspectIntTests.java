package com.bitsmi.springbootshowcase.springcore.aop.test.aspect;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import com.bitsmi.springbootshowcase.springcore.aop.aspect.ProductServiceAspect;
import com.bitsmi.springbootshowcase.springcore.aop.config.AopModuleConfig;
import com.bitsmi.springbootshowcase.springcore.aop.service.ProductService;
import com.bitsmi.springbootshowcase.springcore.aop.testsupport.TestLoggerAppender;
import com.bitsmi.springbootshowcase.springcore.aop.util.IgnoreOnComponentScan;
import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.InjectSoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@ExtendWith({SpringExtension.class, SoftAssertionsExtension.class})
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
@Tag("IntegrationTest")
class ProductServiceAspectIntTests
{
    @Autowired
    private ProductService productService;

    private static TestLoggerAppender appenderMock;

    @InjectSoftAssertions
    private SoftAssertions softly;

    @BeforeAll
    static void setUpAll()
    {
        appenderMock = new TestLoggerAppender();

        final Logger consoleLog = (Logger) LoggerFactory.getLogger(ProductServiceAspect.class);
        consoleLog.addAppender(appenderMock);
        appenderMock.start();
    }

    @AfterEach
    void tearDown()
    {
        appenderMock.clear();
    }

    @AfterAll
    static void tearDownAll()
    {
        final Logger consoleLog = (Logger)LoggerFactory.getLogger(ProductServiceAspect.class);
        appenderMock.stop();
        consoleLog.detachAppender(appenderMock);
    }

    @Test
    @DisplayName("aspect should execute Before and After methods when getProductName service method is executed")
    void getProductNameAspectsExecutionTest1()
    {
        productService.getProductName();

        softly.assertThat(appenderMock.getEvents())
                .as("Log events")
                .hasSize(2);
        softly.assertThat(appenderMock.getEvents().getFirst())
                .as("Before method execution log")
                .satisfies(
                        actualEvent -> {
                            softly.assertThat(actualEvent.getLevel())
                                    .as("Log level")
                                    .isEqualTo(Level.INFO);
                        },
                        actualEvent -> {
                            softly.assertThat(actualEvent.getFormattedMessage())
                                    .as("Message")
                                    .isEqualTo("[beforeProductServiceMethodExecution] Aspect executed before (getProductName)");
                        }
                );
        softly.assertThat(appenderMock.getEvents().get(1))
                .as("After method execution log")
                .satisfies(
                        actualEvent -> {
                            softly.assertThat(actualEvent.getLevel())
                                    .as("Log level")
                                    .isEqualTo(Level.INFO);
                        },
                        actualEvent -> {
                            softly.assertThat(actualEvent.getFormattedMessage())
                                    .as("Message")
                                    .isEqualTo("[afterProductServiceMethodExecution] Aspect executed after (getProductName)");
                        }
                );
    }

    @Test
    @DisplayName("aspect should execute Before and After methods when getProductName service method is executed")
    void getProductReferenceAspectsExecutionTest1()
    {
        productService.getProductReference();

        softly.assertThat(appenderMock.getEvents())
                .as("Log events")
                .hasSize(2);
        softly.assertThat(appenderMock.getEvents().getFirst())
                .as("Before method execution log")
                .satisfies(
                        actualEvent -> {
                            softly.assertThat(actualEvent.getLevel())
                                    .as("Log level")
                                    .isEqualTo(Level.INFO);
                        },
                        actualEvent -> {
                            softly.assertThat(actualEvent.getFormattedMessage())
                                    .as("Message")
                                    .isEqualTo("[beforeProductServiceMethodExecution] Aspect executed before (getProductReference)");
                        }
                );
        softly.assertThat(appenderMock.getEvents().get(1))
                .as("After method execution log")
                .satisfies(
                        actualEvent -> {
                            softly.assertThat(actualEvent.getLevel())
                                    .as("Log level")
                                    .isEqualTo(Level.INFO);
                        },
                        actualEvent -> {
                            softly.assertThat(actualEvent.getFormattedMessage())
                                    .as("Message")
                                    .isEqualTo("[afterProductServiceMethodExecution] Aspect executed after (getProductReference)");
                        }
                );
    }

    /*---------------------------*
     * TEST CONFIG AND HELPERS
     *---------------------------*/
    @TestConfiguration
    @Import({ AopModuleConfig.class })
    @IgnoreOnComponentScan
    // Enable autoconfiguration of AOP starter
    @EnableAutoConfiguration
    static class TestConfig
    {

    }
}
