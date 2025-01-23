package com.bitsmi.springbootshowcase.sampleapps.webmvc.main.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = {
        "file:conf/application.properties"
    },
    ignoreResourceNotFound = true
)
public class MainModuleConfig {

}
