package com.bitsmi.springbootshowcase.springcore.dependencyinjection.test.prototype.service;

import com.bitsmi.springbootshowcase.springcore.dependencyinjection.scoped.config.ScopedServiceConfig;
import com.bitsmi.springbootshowcase.springcore.dependencyinjection.scoped.service.ParameterizedService;
import com.bitsmi.springbootshowcase.springcore.dependencyinjection.scoped.service.ParameterizedServicePrototypeFactory;
import com.bitsmi.springbootshowcase.springcore.dependencyinjection.scoped.service.PrototypeService;
import com.bitsmi.springbootshowcase.springcore.dependencyinjection.util.IgnoreOnComponentScan;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith({ SpringExtension.class })
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
@Tag("IntegrationTest")
class PrototypeServicesIntTests
{
    @Autowired
    private PrototypeService prototypeService1;
    @Autowired
    private PrototypeService prototypeService2;

    @Autowired
    private ObjectProvider<PrototypeService> prototypeServiceObjectProvider;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private ParameterizedServicePrototypeFactory parameterizedServicePrototypeFactory;

    @Autowired
    private ObjectProvider<ParameterizedService> parameterizedServiceObjectProvider;

    @Test
    @DisplayName("spring should inject different instances of a prototype bean when is autowired in different injection points")
    void injectionPointsTest1() {
        assertThat(prototypeService1).isNotNull();
        assertThat(prototypeService2).isNotNull()
                .isNotSameAs(prototypeService1);
    }

    @Test
    @DisplayName("prototypeServiceObjectProvider should provide a new instance when call getObject method")
    void prototypeServiceObjectProviderTest1()
    {
        final PrototypeService actualInstance1 = prototypeServiceObjectProvider.getObject();
        final PrototypeService actualInstance2 = prototypeServiceObjectProvider.getObject();

        assertThat(actualInstance1).isNotSameAs(actualInstance2);
    }

    @Test
    @DisplayName("applicationContext should provide a new instance when call getBean method")
    void applicationContextTest1()
    {
        final PrototypeService actualInstance1 = applicationContext.getBean(PrototypeService.class);
        final PrototypeService actualInstance2 = applicationContext.getBean(PrototypeService.class);

        assertThat(actualInstance1).isNotSameAs(actualInstance2);
    }

    @Test
    @DisplayName("parameterizedServicePrototypeFactory should provide a new instance given a name when call get method")
    void parameterizedServicePrototypeFactoryTest1()
    {
        final String givenName1 = "NAME_1";
        final String givenName2 = "NAME_2";

        final ParameterizedService actualInstance1 = parameterizedServicePrototypeFactory.get("NAME_1");
        final ParameterizedService actualInstance2 = parameterizedServicePrototypeFactory.get("NAME_2");

        assertThat(actualInstance1.getName()).isEqualTo(givenName1);
        assertThat(actualInstance2.getName()).isEqualTo(givenName2);
    }

    @Test
    @DisplayName("parameterizedServicePrototypeFactory should provide always new instances given a name when call multiple times with same value")
    void parameterizedServicePrototypeFactoryTest2()
    {
        final String givenName = "A_NAME";

        final ParameterizedService actualInstance1 = parameterizedServicePrototypeFactory.get(givenName);
        final ParameterizedService actualInstance2 = parameterizedServicePrototypeFactory.get(givenName);

        assertThat(actualInstance1).isNotNull();
        assertThat(actualInstance2)
                .isNotNull()
                .isNotSameAs(actualInstance1);
    }

    @Test
    @DisplayName("parameterizedServiceObjectProvider should provide a new instance given a name when call getObject method")
    void parameterizedServiceObjectProviderTest1()
    {
        final String givenName1 = "NAME_1";
        final String givenName2 = "NAME_2";

        final ParameterizedService actualInstance1 = parameterizedServiceObjectProvider.getObject("NAME_1");
        final ParameterizedService actualInstance2 = parameterizedServiceObjectProvider.getObject("NAME_2");

        assertThat(actualInstance1.getName()).isEqualTo(givenName1);
        assertThat(actualInstance2.getName()).isEqualTo(givenName2);
    }

    @Test
    @DisplayName("parameterizedServiceObjectProvider should provide always new instances given a name when call multiple times with same value")
    void parameterizedServiceObjectProviderTest2()
    {
        final String givenName = "A_NAME";

        final ParameterizedService actualInstance1 = parameterizedServiceObjectProvider.getObject(givenName);
        final ParameterizedService actualInstance2 = parameterizedServiceObjectProvider.getObject(givenName);

        assertThat(actualInstance1).isNotNull();
        assertThat(actualInstance2)
                .isNotNull()
                .isNotSameAs(actualInstance1);
    }

    /*---------------------------*
     * TEST CONFIG AND HELPERS
     *---------------------------*/
    @TestConfiguration
    @Import({ ScopedServiceConfig.class })
    @IgnoreOnComponentScan
    static class TestConfig
    {

    }
}
