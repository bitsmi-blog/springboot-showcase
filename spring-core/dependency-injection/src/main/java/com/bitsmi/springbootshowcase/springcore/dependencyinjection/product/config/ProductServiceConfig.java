package com.bitsmi.springbootshowcase.springcore.dependencyinjection.product.config;

import com.bitsmi.springbootshowcase.springcore.dependencyinjection.product.ProductPackage;
import com.bitsmi.springbootshowcase.springcore.dependencyinjection.util.IgnoreOnComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
        basePackageClasses = { ProductPackage.class },
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = IgnoreOnComponentScan.class)
)
public class ProductServiceConfig
{

}
