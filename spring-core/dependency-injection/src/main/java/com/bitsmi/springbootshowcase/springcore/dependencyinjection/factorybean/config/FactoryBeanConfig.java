package com.bitsmi.springbootshowcase.springcore.dependencyinjection.factorybean.config;

import com.bitsmi.springbootshowcase.springcore.dependencyinjection.factorybean.service.DependencyService;
import com.bitsmi.springbootshowcase.springcore.dependencyinjection.factorybean.service.impl.ComplexServiceFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FactoryBeanConfig {

    @Bean("complexService")
    public ComplexServiceFactoryBean complexServiceFactory(DependencyService dependencyService) {
        return new ComplexServiceFactoryBean(dependencyService)
            .setConfigurationValue("configuration_value");
    }
}
