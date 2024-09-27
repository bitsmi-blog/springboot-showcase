package com.bitsmi.springbootshowcase.springcore.dependencyinjection.factorybean.service.impl;

import com.bitsmi.springbootshowcase.springcore.dependencyinjection.factorybean.service.DependencyService;
import org.springframework.stereotype.Service;

@Service
public class DependencyServiceImpl implements DependencyService {

    @Override
    public String getValue() {
        return "dependency_service_value";
    }
}
