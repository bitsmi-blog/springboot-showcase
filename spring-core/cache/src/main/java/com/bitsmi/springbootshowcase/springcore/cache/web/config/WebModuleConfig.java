package com.bitsmi.springbootshowcase.springcore.cache.web.config;

import com.bitsmi.springbootshowcase.springcore.cache.domain.common.util.IgnoreOnComponentScan;
import com.bitsmi.springbootshowcase.springcore.cache.web.WebPackage;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
        basePackageClasses = { WebPackage.class },
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = IgnoreOnComponentScan.class)
)
public class WebModuleConfig
{

}
