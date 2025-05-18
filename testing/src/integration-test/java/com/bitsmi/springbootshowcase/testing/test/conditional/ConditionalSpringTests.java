package com.bitsmi.springbootshowcase.testing.test.conditional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.EnabledIf;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
@Tag("IntegrationTest")
@ActiveProfiles({ "SAMPLE_PROFILE", "DEFAULT_PROFILE" })
class ConditionalSpringTests
{
    @Autowired
    private ApplicationContext applicationContext;

    @Test
    @DisplayName("test should be executed given active profile")
    @EnabledIf(value = "#{environment.getActiveProfiles()[0] == 'SAMPLE_PROFILE'}", loadContext = true)
    void enabledTest1()
    {
        assertThat(applicationContext).isNotNull();
    }

    @Test
    @DisplayName("test should not be executed given no active profile")
    @EnabledIf(value = "#{environment.getActiveProfiles()[0] != 'SAMPLE_PROFILE'}", loadContext = true)
    void enabledTest2()
    {
        fail();
    }
}
