package com.bitsmi.springbootshowcase.main.config;

import com.bitsmi.springbootshowcase.application.config.ApplicationModuleConfig;
import com.bitsmi.springbootshowcase.core.config.CoreModuleConfig;
import com.bitsmi.springbootshowcase.web.config.WebModuleConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Import({
        WebModuleConfig.class,
        ApplicationModuleConfig.class,
        CoreModuleConfig.class
})
@PropertySource(value = {
        "file:conf/application.properties"
    },
    ignoreResourceNotFound = true
)
public class MainModuleConfig
{

}
