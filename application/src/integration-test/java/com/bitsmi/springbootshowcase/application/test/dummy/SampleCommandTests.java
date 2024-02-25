package com.bitsmi.springbootshowcase.application.test.dummy;

import com.bitsmi.springbootshowcase.application.config.ApplicationModuleConfig;
import com.bitsmi.springbootshowcase.application.dummy.ISampleApplicationCommand;
import com.bitsmi.springbootshowcase.application.test.config.DomainModuleMockConfig;
import com.bitsmi.springbootshowcase.domain.common.util.IgnoreOnComponentScan;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
@Tag("IntegrationTest")
public class SampleCommandTests
{
    private static final Logger LOGGER = LoggerFactory.getLogger(SampleCommandTests.class);

    @Autowired
    private ObjectProvider<ISampleApplicationCommand> sampleCommandProvider;

    @Test
    @DisplayName("provider should create an instance with specified parameters")
    public void providerTest1()
    {
        ISampleApplicationCommand command = sampleCommandProvider.getObject("Param 1");

        String message = command.getMessage();

        assertThat(message).isEqualTo("Sample One: Param 1");
    }

    @Test
    @DisplayName("provider should create prototype scoped beans")
    public void providerTest2()
    {
        ISampleApplicationCommand command1 = sampleCommandProvider.getObject("Param 1");
        ISampleApplicationCommand command2 = sampleCommandProvider.getObject("Param 1");

        LOGGER.info("Command 1: %s".formatted(command1));
        LOGGER.info("Command 2: %s".formatted(command2));

        assertThat(command1).isNotSameAs(command2);
    }

    /*---------------------------*
     * SETUP AND HELPERS
     *---------------------------*/
    @TestConfiguration
    @Import({ ApplicationModuleConfig.class, DomainModuleMockConfig.class })
    @EnableAspectJAutoProxy
    @IgnoreOnComponentScan
    static class TestConfig
    {
        @Bean
        public MeterRegistry meterRegistry()
        {
            return new SimpleMeterRegistry();
        }
    }
}
