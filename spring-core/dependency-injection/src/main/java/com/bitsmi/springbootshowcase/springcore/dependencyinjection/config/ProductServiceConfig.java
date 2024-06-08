package com.bitsmi.springbootshowcase.springcore.dependencyinjection.config;

import com.bitsmi.springbootshowcase.springcore.dependencyinjection.service.ServicePackage;
import com.bitsmi.springbootshowcase.springcore.dependencyinjection.util.IgnoreOnComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
        basePackageClasses = { ServicePackage.class },
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = IgnoreOnComponentScan.class)
)
public class ProductServiceConfig
{

}
