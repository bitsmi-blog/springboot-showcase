package com.bitsmi.springbootshowcase.sampleapps.kafka.main.config;

import com.bitsmi.springbootshowcase.sampleapps.application.config.ApplicationModuleConfig;
import com.bitsmi.springbootshowcase.sampleapps.infrastructure.config.InfrastructureModuleConfig;
import com.bitsmi.springbootshowcase.sampleapps.kafka.messaging.config.MessagingModuleConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Import({
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
