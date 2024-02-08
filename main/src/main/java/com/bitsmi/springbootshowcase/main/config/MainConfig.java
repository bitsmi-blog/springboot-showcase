package com.bitsmi.springbootshowcase.main.config;

import com.bitsmi.springbootshowcase.web.config.WebConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Import({
        WebConfig.class
})
@PropertySource(value = {
        "file:conf/application.properties"
    },
    ignoreResourceNotFound = true
)
public class MainConfig
{

}
