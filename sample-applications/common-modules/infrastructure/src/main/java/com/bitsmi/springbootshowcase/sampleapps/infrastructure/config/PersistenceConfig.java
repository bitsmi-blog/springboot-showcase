package com.bitsmi.springbootshowcase.sampleapps.infrastructure.config;

import com.bitsmi.springbootshowcase.sampleapps.infrastructure.InfrastructureModulePackage;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackageClasses = {
        InfrastructureModulePackage.class,
})
@EntityScan(basePackageClasses = {
        InfrastructureModulePackage.class
})
public class PersistenceConfig
{

}
