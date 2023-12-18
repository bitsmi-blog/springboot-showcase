package com.bitsmi.springbootshowcase.core.dummy.config;

import com.bitsmi.springbootshowcase.core.dummy.aop.DummyAspect;
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
