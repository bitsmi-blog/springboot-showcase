package com.bitsmi.springbootshowcase.springcore.dependencyinjection.factorybean.service.impl;

import com.bitsmi.springbootshowcase.springcore.dependencyinjection.factorybean.service.ComplexService;
import com.bitsmi.springbootshowcase.springcore.dependencyinjection.factorybean.service.DependencyService;

public class ComplexServiceImpl implements ComplexService {

    private final DependencyService dependencyService;

    private String configurationValue;

    public ComplexServiceImpl(DependencyService dependencyService) {
        this.dependencyService = dependencyService;
    }

    public ComplexServiceImpl setConfigurationValue(String configurationValue) {
        this.configurationValue = configurationValue;
        return this;
    }

    @Override
    public String doStuff() {
        return "Doing some stuff using (%s) configuration value and (%s) value from dependency".formatted(
            configurationValue,
            dependencyService.getValue()
        );
    }
}
