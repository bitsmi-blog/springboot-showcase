package com.bitsmi.springbootshowcase.infrastructure.config;

import com.bitsmi.springbootshowcase.infrastructure.IInfrastructurePackage;
import com.bitsmi.springbootshowcase.domain.common.util.IgnoreOnComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
    basePackageClasses = { IInfrastructurePackage.class },
    excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = IgnoreOnComponentScan.class)
)
public class InfrastructureModuleConfig
{

}
