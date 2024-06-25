package com.bitsmi.springbootshowcase.springdatajpa.repositoryextension.infrastructure.config;

import com.bitsmi.springbootshowcase.springdatajpa.repositoryextension.infrastructure.InfrastructurePackage;
import com.bitsmi.springbootshowcase.springdatajpa.repositoryextension.infrastructure.common.repository.impl.CustomBaseRepositoryImpl;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackageClasses = {
        InfrastructurePackage.class,
},
        repositoryBaseClass = CustomBaseRepositoryImpl.class
)
@EntityScan(basePackageClasses = {
        InfrastructurePackage.class
})
public class PersistenceConfig
{

}
