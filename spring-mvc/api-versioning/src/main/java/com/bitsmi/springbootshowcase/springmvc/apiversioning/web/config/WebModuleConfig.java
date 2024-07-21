package com.bitsmi.springbootshowcase.springmvc.apiversioning.web.config;

import com.bitsmi.springbootshowcase.springmvc.apiversioning.web.WebPackage;
import com.bitsmi.springbootshowcase.utils.IgnoreOnComponentScan;
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
