package com.bitsmi.springbootshowcase.springdatajpa.criteriabuilder.infrastructure.config;

import com.bitsmi.springbootshowcase.springdatajpa.criteriabuilder.infrastructure.InfrastructurePackage;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackageClasses = {
        InfrastructurePackage.class
})
@EntityScan(basePackageClasses = {
        InfrastructurePackage.class
})
public class PersistenceConfig
{

}
