package com.bitsmi.springbootshowcase.springcore.cache.application.config;

import com.bitsmi.springbootshowcase.springcore.cache.application.ApplicationPackage;
import com.bitsmi.springbootshowcase.utils.IgnoreOnComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
        basePackageClasses = { ApplicationPackage.class },
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = IgnoreOnComponentScan.class)
)
public class ApplicationModuleConfig
{

}
