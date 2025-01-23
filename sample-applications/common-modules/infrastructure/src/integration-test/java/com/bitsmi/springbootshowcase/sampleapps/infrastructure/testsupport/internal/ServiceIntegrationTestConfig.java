package com.bitsmi.springbootshowcase.sampleapps.infrastructure.testsupport.internal;

import com.bitsmi.springbootshowcase.sampleapps.infrastructure.InfrastructureModulePackage;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@TestConfiguration
@ComponentScan(basePackageClasses = InfrastructureModulePackage.class)
public class ServiceIntegrationTestConfig {

    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
