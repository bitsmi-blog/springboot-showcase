package com.bitsmi.springbootshowcase.springcore.cache.infrastructure.config;

import com.bitsmi.springbootshowcase.springcore.cache.domain.common.util.IgnoreOnComponentScan;
import com.bitsmi.springbootshowcase.springcore.cache.infrastructure.InfrastructurePackage;
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
