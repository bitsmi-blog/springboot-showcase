package com.bitsmi.springbootshowcase.application.config;

import com.bitsmi.springbootshowcase.application.IApplicationPackage;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = IApplicationPackage.class)
public class ApplicationConfig
{

}
