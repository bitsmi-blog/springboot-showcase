package com.bitsmi.springbootshowcase.sampleapps.webapp.main.config;

import com.bitsmi.springbootshowcase.sampleapps.application.config.ApplicationModuleConfig;
import com.bitsmi.springbootshowcase.sampleapps.infrastructure.config.InfrastructureModuleConfig;
import com.bitsmi.springbootshowcase.sampleapps.webapp.web.config.WebModuleConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Import({
        WebModuleConfig.class,
        ApplicationModuleConfig.class,
        InfrastructureModuleConfig.class
})
@PropertySource(value = {
        "file:conf/application.properties"
    },
    ignoreResourceNotFound = true
)
public class MainModuleConfig
{

}
