package com.bitsmi.springbootshowcase.main.config;

import com.bitsmi.springbootshowcase.application.config.ApplicationModuleConfig;
import com.bitsmi.springbootshowcase.infrastructure.config.InfrastructureModuleConfig;
import com.bitsmi.springbootshowcase.messaging.config.MessagingModuleConfig;
import com.bitsmi.springbootshowcase.web.config.WebModuleConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Import({
        WebModuleConfig.class,
        MessagingModuleConfig.class,
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
