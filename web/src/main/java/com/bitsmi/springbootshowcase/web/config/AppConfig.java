package com.bitsmi.springbootshowcase.web.config;

import com.bitsmi.springbootshowcase.core.ICorePackage;
import com.bitsmi.springbootshowcase.core.common.config.CoreConfig;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@Import({ CoreConfig.class })
@EnableJpaRepositories(basePackageClasses = {
    ICorePackage.class,
})
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
