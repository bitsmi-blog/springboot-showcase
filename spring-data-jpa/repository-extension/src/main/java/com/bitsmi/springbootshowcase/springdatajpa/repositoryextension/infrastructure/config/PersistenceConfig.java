package com.bitsmi.springbootshowcase.springdatajpa.repositoryextension.infrastructure.config;

import com.bitsmi.springbootshowcase.springdatajpa.repositoryextension.infrastructure.IInfrastructurePackage;
import com.bitsmi.springbootshowcase.springdatajpa.repositoryextension.infrastructure.common.repository.impl.CustomBaseRepositoryImpl;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackageClasses = {
        IInfrastructurePackage.class,
},
        repositoryBaseClass = CustomBaseRepositoryImpl.class
)
@EntityScan(basePackageClasses = {
        IInfrastructurePackage.class
})
public class PersistenceConfig
{

}
