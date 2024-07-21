package com.bitsmi.springbootshowcase.sampleapps.infrastructure.config;

import com.bitsmi.springbootshowcase.sampleapps.infrastructure.InfrastructurePackage;
import com.bitsmi.springbootshowcase.utils.IgnoreOnComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
    basePackageClasses = { InfrastructurePackage.class },
    excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = IgnoreOnComponentScan.class)
)
public class InfrastructureModuleConfig
{

}
