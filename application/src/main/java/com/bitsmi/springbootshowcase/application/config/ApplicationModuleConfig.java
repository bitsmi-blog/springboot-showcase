package com.bitsmi.springbootshowcase.application.config;

import com.bitsmi.springbootshowcase.application.IApplicationPackage;
import com.bitsmi.springbootshowcase.domain.common.util.IgnoreOnComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
        basePackageClasses = { IApplicationPackage.class },
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = IgnoreOnComponentScan.class)
)
public class ApplicationModuleConfig
{

}
