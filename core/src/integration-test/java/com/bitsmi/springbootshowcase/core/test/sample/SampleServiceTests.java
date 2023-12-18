package com.bitsmi.springbootshowcase.core.test.sample;

import com.bitsmi.springbootshowcase.core.dummy.ISampleService;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.EnabledIf;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
@Tag("IntegrationTest")
@ActiveProfiles({ "SAMPLE_PROFILE", "DEFAULT_PROFILE" })
public class SampleServiceTests
{
    @Autowired
    private ISampleService defaultSampleService;
    @Autowired
    @Qualifier("sampleTwo")
    private ISampleService sampleServiceTwo;
    @Autowired
    private List<ISampleService> allSampleServices;

    @Test
    @DisplayName("should inject the primary sample service")
    public void sampleServiceInjectionTest1()
    {
        String sample = defaultSampleService.getSample();
        assertThat(sample).isEqualTo("Sample One");
    }

    @Test
    @DisplayName("should inject the qualified sample service")
    public void sampleServiceInjectionTest2()
    {
        String sample = sampleServiceTwo.getSample();
        assertThat(sample).isEqualTo("Sample Two");
    }

    @Test
    @DisplayName("should inject multiple sample service given no profile")
    @EnabledIf(value = "#{environment.getActiveProfiles()[0] != 'SAMPLE_PROFILE'}", loadContext = true)
    public void sampleServiceInjectionTest3()
    {
        List<String> samples = allSampleServices.stream()
                .map(ISampleService::getSample)
                .toList();

        assertThat(allSampleServices).hasSize(2);
        assertThat(samples).containsExactlyInAnyOrder("Sample One", "Sample Two");
    }

    @Test
    @DisplayName("should inject multiple sample service given a profile")
    @EnabledIf(value = "#{environment.getActiveProfiles()[0] == 'SAMPLE_PROFILE'}", loadContext = true)
    public void sampleServiceInjectionTest4()
    {
        List<String> samples = allSampleServices.stream()
                .map(ISampleService::getSample)
                .toList();

        assertThat(allSampleServices).hasSize(3);
        assertThat(samples).containsExactlyInAnyOrder("Sample One", "Sample Two", "Sample Three");
    }

    /*---------------------------*
     * SETUP AND HELPERS
     *---------------------------*/
    @TestConfiguration
    @EnableAspectJAutoProxy
    @ComponentScan(basePackageClasses = ISampleService.class)
    static class TestConfig
    {
        @Bean
        public MeterRegistry meterRegistry()
        {
            return new SimpleMeterRegistry();
        }
    }
}
