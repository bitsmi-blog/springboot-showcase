package com.bitsmi.springbootshowcase.web.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource({
        "file:conf/application.properties"
})
public class AppConfig
{

}
