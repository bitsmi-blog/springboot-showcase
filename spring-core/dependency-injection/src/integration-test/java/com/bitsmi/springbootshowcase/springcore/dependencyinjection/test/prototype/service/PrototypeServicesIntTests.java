package com.bitsmi.springbootshowcase.springcore.dependencyinjection.test.prototype.service;

import com.bitsmi.springbootshowcase.springcore.dependencyinjection.scoped.ScopedPackage;
import com.bitsmi.springbootshowcase.springcore.dependencyinjection.scoped.service.ParameterizedService;
import com.bitsmi.springbootshowcase.springcore.dependencyinjection.scoped.service.ParameterizedServicePrototypeFactory;
import com.bitsmi.springbootshowcase.springcore.dependencyinjection.scoped.service.PrototypeService;
import com.bitsmi.springbootshowcase.utils.IgnoreOnComponentScan;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith({ SpringExtension.class })
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
@Tag("IntegrationTest")
class PrototypeServicesIntTests {

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
    void prototypeServiceObjectProviderTest1() {
        final PrototypeService actualInstance1 = prototypeServiceObjectProvider.getObject();
        final PrototypeService actualInstance2 = prototypeServiceObjectProvider.getObject();

        assertThat(actualInstance1).isNotSameAs(actualInstance2);
    }

    @Test
    @DisplayName("applicationContext should provide a new instance when call getBean method")
    void applicationContextTest1() {
        final PrototypeService actualInstance1 = applicationContext.getBean(PrototypeService.class);
        final PrototypeService actualInstance2 = applicationContext.getBean(PrototypeService.class);

        assertThat(actualInstance1).isNotSameAs(actualInstance2);
    }

    @Test
    @DisplayName("parameterizedServicePrototypeFactory should provide a new instance when call get method")
    void parameterizedServicePrototypeFactoryTest1() {
        final String providedName1 = "NAME_1";
        final String providedName2 = "NAME_2";

        final ParameterizedService actualInstance1 = parameterizedServicePrototypeFactory.get("NAME_1");
        final ParameterizedService actualInstance2 = parameterizedServicePrototypeFactory.get("NAME_2");

        assertThat(actualInstance1.getName()).isEqualTo(providedName1);
        assertThat(actualInstance2.getName()).isEqualTo(providedName2);
    }

    @Test
    @DisplayName("parameterizedServicePrototypeFactory should provide always new instances when call multiple times with same value")
    void parameterizedServicePrototypeFactoryTest2() {
        final String providedName = "A_NAME";

        final ParameterizedService actualInstance1 = parameterizedServicePrototypeFactory.get(providedName);
        final ParameterizedService actualInstance2 = parameterizedServicePrototypeFactory.get(providedName);

        assertThat(actualInstance1).isNotNull();
        assertThat(actualInstance2)
                .isNotNull()
                .isNotSameAs(actualInstance1);
    }

    @Test
    @DisplayName("parameterizedServiceObjectProvider should return new instances when call getObject method with different values")
    void parameterizedServiceObjectProviderTest1() {
        final String providedName1 = "NAME_1";
        final String providedName2 = "NAME_2";

        final ParameterizedService actualInstance1 = parameterizedServiceObjectProvider.getObject("NAME_1");
        final ParameterizedService actualInstance2 = parameterizedServiceObjectProvider.getObject("NAME_2");

        assertThat(actualInstance1.getName()).isEqualTo(providedName1);
        assertThat(actualInstance2.getName()).isEqualTo(providedName2);
    }

    @Test
    @DisplayName("parameterizedServiceObjectProvider should return always new instances when call getObject method multiple times with same value")
    void parameterizedServiceObjectProviderTest2() {
        final String providedName = "A_NAME";

        final ParameterizedService actualInstance1 = parameterizedServiceObjectProvider.getObject(providedName);
        final ParameterizedService actualInstance2 = parameterizedServiceObjectProvider.getObject(providedName);

        assertThat(actualInstance1).isNotNull();
        assertThat(actualInstance2)
                .isNotNull()
                .isNotSameAs(actualInstance1);
    }

    /*---------------------------*
     * TEST CONFIG AND HELPERS
     *---------------------------*/
    @TestConfiguration
    @ComponentScan(
            basePackageClasses = { ScopedPackage.class },
            excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = IgnoreOnComponentScan.class)
    )
    static class TestConfig {

    }
}
