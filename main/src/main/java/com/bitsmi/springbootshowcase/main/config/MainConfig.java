package com.bitsmi.springbootshowcase.main.config;

import com.bitsmi.springbootshowcase.application.config.ApplicationConfig;
import com.bitsmi.springbootshowcase.core.config.CoreConfig;
import com.bitsmi.springbootshowcase.web.config.WebConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Import({
        WebConfig.class,
        ApplicationConfig.class,
        CoreConfig.class
})
@PropertySource(value = {
        "file:conf/application.properties"
    },
    ignoreResourceNotFound = true
)
public class MainConfig
{

}
