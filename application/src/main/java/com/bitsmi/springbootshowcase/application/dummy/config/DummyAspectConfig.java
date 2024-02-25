package com.bitsmi.springbootshowcase.application.dummy.config;

import com.bitsmi.springbootshowcase.application.dummy.aop.DummyAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DummyAspectConfig
{
    @Bean
    public DummyAspect dummyASpect()
    {
        return new DummyAspect();
    }
}
