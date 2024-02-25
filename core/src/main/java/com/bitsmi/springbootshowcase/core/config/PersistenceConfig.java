package com.bitsmi.springbootshowcase.core.config;

import com.bitsmi.springbootshowcase.core.ICorePackage;
import com.bitsmi.springbootshowcase.core.common.repository.impl.ICustomBaseRepositoryImpl;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackageClasses = {
        ICorePackage.class,
},
        repositoryBaseClass = ICustomBaseRepositoryImpl.class
)
@EntityScan(basePackageClasses = {
        ICorePackage.class
})
public class PersistenceConfig
{

}
