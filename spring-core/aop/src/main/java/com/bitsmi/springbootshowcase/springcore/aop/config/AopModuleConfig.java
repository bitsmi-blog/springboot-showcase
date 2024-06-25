package com.bitsmi.springbootshowcase.springcore.aop.config;

import com.bitsmi.springbootshowcase.springcore.aop.AopPackage;
import com.bitsmi.springbootshowcase.springcore.aop.util.IgnoreOnComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
        basePackageClasses = { AopPackage.class },
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = IgnoreOnComponentScan.class)
)
public class AopModuleConfig
{

}
