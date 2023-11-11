package com.bitsmi.springbootshowcase.core.common.config;

import com.bitsmi.springbootshowcase.core.ICorePackage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

@Configuration
@ComponentScan(basePackageClasses = { ICorePackage.class })
public class CoreConfig
{
    /**
     * Validator for bean method parameter
     */
    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor()
    {
        return new MethodValidationPostProcessor();
    }
}
