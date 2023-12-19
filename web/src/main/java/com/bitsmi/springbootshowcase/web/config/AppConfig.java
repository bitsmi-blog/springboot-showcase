package com.bitsmi.springbootshowcase.web.config;

import com.bitsmi.springbootshowcase.core.ICorePackage;
import com.bitsmi.springbootshowcase.core.common.config.CoreConfig;
import com.bitsmi.springbootshowcase.core.common.repository.impl.ICustomBaseRepositoryImpl;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@Import({ CoreConfig.class })
@EnableConfigurationProperties({
        JWTProperties.class
})
@EnableJpaRepositories(basePackageClasses = {
        ICorePackage.class,
    },
    repositoryBaseClass = ICustomBaseRepositoryImpl.class
)
@EntityScan(basePackageClasses = {
    ICorePackage.class
})
@PropertySource(value = {
        "file:conf/application.properties"
    },
    ignoreResourceNotFound = true
)
public class AppConfig
{

}
