package com.bitsmi.springbootshowcase.springcore.dependencyinjection.test.factorybean.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.bitsmi.springbootshowcase.springcore.dependencyinjection.factorybean.config.FactoryBeanConfig;
import com.bitsmi.springbootshowcase.springcore.dependencyinjection.factorybean.service.ComplexService;
import com.bitsmi.springbootshowcase.springcore.dependencyinjection.factorybean.service.impl.ComplexServiceFactoryBean;
import com.bitsmi.springbootshowcase.utils.IgnoreOnComponentScan;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@ExtendWith({ SpringExtension.class })
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
@Tag("IntegrationTest")
class ComplexServiceFactoryBeanTests {

    @Autowired
    private ComplexService complexService;

    @Autowired
    private ComplexServiceFactoryBean complexServiceFactoryBean;

    @Autowired
    private ApplicationContext context;

    @Test
    @DisplayName("Autowired values should allow retrieve bean and factory bean instances")
    void complexServiceFactoryBeanTest1() throws Exception {
        ComplexService serviceFromFactory = complexServiceFactoryBean.getObject();

        assertThat(serviceFromFactory).isSameAs(complexService);
        assertThat(serviceFromFactory.doStuff())
            .isEqualTo(complexService.doStuff());
    }

    @Test
    @DisplayName("getBean should allow retrieve bean and factory bean instances")
    void complexServiceFactoryBeanTest2() throws Exception {
        // It is needed to add a '&' to the bean name to retrieve factory instance and then the bean instance through the getObjectMethod
        ComplexService localService1 = ((ComplexServiceFactoryBean) context.getBean("&complexService")).getObject();
        // And use the bean name directly to retrieve the bean instance
        ComplexService localService2 = (ComplexService) context.getBean("complexService");

        assertThat(localService1).isSameAs(localService2);
        assertThat(localService2).isSameAs(complexService);
        assertThat(localService1.doStuff())
            .isEqualTo(complexService.doStuff());
    }

    /*---------------------------*
     * TEST CONFIG AND HELPERS
     *---------------------------*/
    @TestConfiguration
    @Import({ FactoryBeanConfig.class })
    @IgnoreOnComponentScan
    static class TestConfig
    {

    }
}
