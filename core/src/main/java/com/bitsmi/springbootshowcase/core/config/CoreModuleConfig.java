package com.bitsmi.springbootshowcase.core.config;

import com.bitsmi.springbootshowcase.core.ICorePackage;
import com.bitsmi.springbootshowcase.domain.common.util.IgnoreOnComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
    basePackageClasses = { ICorePackage.class },
    excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = IgnoreOnComponentScan.class)
)
public class CoreModuleConfig
{

}
