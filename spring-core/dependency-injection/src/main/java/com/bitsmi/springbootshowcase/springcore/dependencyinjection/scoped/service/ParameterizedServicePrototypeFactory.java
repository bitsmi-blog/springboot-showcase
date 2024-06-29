package com.bitsmi.springbootshowcase.springcore.dependencyinjection.scoped.service;

import com.bitsmi.springbootshowcase.springcore.dependencyinjection.scoped.service.impl.ParameterizedServiceImpl;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class ParameterizedServicePrototypeFactory
{
    @Bean
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public ParameterizedService get(String name)
    {
        return new ParameterizedServiceImpl(name);
    }
}
