package com.bitsmi.springbootshowcase.infrastructure.config;

import com.bitsmi.springbootshowcase.infrastructure.IInfrastructurePackage;
import com.bitsmi.springbootshowcase.infrastructure.common.repository.impl.ICustomBaseRepositoryImpl;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackageClasses = {
        IInfrastructurePackage.class,
},
        repositoryBaseClass = ICustomBaseRepositoryImpl.class
)
@EntityScan(basePackageClasses = {
        IInfrastructurePackage.class
})
public class PersistenceConfig
{

}