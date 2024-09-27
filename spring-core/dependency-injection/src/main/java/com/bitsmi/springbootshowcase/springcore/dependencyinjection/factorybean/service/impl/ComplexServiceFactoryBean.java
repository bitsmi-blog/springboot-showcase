package com.bitsmi.springbootshowcase.springcore.dependencyinjection.factorybean.service.impl;

import com.bitsmi.springbootshowcase.springcore.dependencyinjection.factorybean.service.ComplexService;
import com.bitsmi.springbootshowcase.springcore.dependencyinjection.factorybean.service.DependencyService;
import org.springframework.beans.factory.config.AbstractFactoryBean;

public class ComplexServiceFactoryBean extends AbstractFactoryBean<ComplexService> {

    private final DependencyService dependencyService;

    private String configurationValue;

    public ComplexServiceFactoryBean(DependencyService dependencyService) {
        this.dependencyService = dependencyService;
        // Singleton by default. Set `false` to create prototype instances
//        setSingleton(false);
    }

    public ComplexServiceFactoryBean setConfigurationValue(String configurationValue) {
        this.configurationValue = configurationValue;
        return this;
    }

    @Override
    public Class<?> getObjectType() {
        return ComplexService.class;
    }

    @Override
    protected ComplexService createInstance() throws Exception {
        return new ComplexServiceImpl(dependencyService)
            .setConfigurationValue(configurationValue);
    }
}
