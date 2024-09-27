package com.bitsmi.springbootshowcase.springcore.dependencyinjection.factorybean.config;

import com.bitsmi.springbootshowcase.springcore.dependencyinjection.factorybean.FactoryBeanPackage;
import com.bitsmi.springbootshowcase.springcore.dependencyinjection.factorybean.service.DependencyService;
import com.bitsmi.springbootshowcase.springcore.dependencyinjection.factorybean.service.impl.ComplexServiceFactoryBean;
import com.bitsmi.springbootshowcase.utils.IgnoreOnComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
    basePackageClasses = { FactoryBeanPackage.class },
    excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = IgnoreOnComponentScan.class)
)
public class FactoryBeanConfig {

    @Bean("complexService")
    public ComplexServiceFactoryBean complexServiceFactory(DependencyService dependencyService) {
        return new ComplexServiceFactoryBean(dependencyService)
            .setConfigurationValue("configuration_value");
    }
}
