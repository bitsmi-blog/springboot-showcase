package com.bitsmi.springbootshowcase.clients.openfeign.server;

import com.bitsmi.springbootshowcase.clients.openfeign.server.util.IgnoreOnComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ComponentScan(
        basePackageClasses = { ServerPackage.class },
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = IgnoreOnComponentScan.class)
)
public class ServerModuleConfig implements WebMvcConfigurer
{

}
