package com.bitsmi.springbootshowcase.sampleapps.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

@Configuration
public class ValidationConfig
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
