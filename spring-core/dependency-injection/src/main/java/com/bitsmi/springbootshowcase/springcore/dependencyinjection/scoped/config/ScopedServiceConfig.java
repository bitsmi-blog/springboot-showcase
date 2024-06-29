package com.bitsmi.springbootshowcase.springcore.dependencyinjection.scoped.config;

import com.bitsmi.springbootshowcase.springcore.dependencyinjection.scoped.ScopedPackage;
import com.bitsmi.springbootshowcase.springcore.dependencyinjection.util.IgnoreOnComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
        basePackageClasses = { ScopedPackage.class },
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = IgnoreOnComponentScan.class)
)
public class ScopedServiceConfig
{

}
