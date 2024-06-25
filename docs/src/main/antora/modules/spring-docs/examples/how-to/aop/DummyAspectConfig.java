package com.bitsmi.springbootshowcase.sampleapps.application.dummy.config;

import com.bitsmi.springbootshowcase.sampleapps.application.dummy.aop.DummyAspect;
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
