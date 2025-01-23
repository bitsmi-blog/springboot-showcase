package com.bitsmi.springbootshowcase.sampleapps.kafka.main;

import com.bitsmi.springbootshowcase.sampleapps.application.ApplicationModulePackage;
import com.bitsmi.springbootshowcase.sampleapps.infrastructure.InfrastructureModulePackage;
import com.bitsmi.springbootshowcase.sampleapps.kafka.messaging.MessagingModulePackage;
import com.bitsmi.springbootshowcase.utils.IgnoreOnComponentScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

// @SpringBootApplication == { @Configuration @EnableAutoConfiguration @ComponentScan }
@Configuration
@EnableAutoConfiguration
@ComponentScan(
        basePackageClasses = {
                MainModulePackage.class,
                MessagingModulePackage.class,
                ApplicationModulePackage.class,
                InfrastructureModulePackage.class
        },
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = IgnoreOnComponentScan.class)
        }
)
public class MainProgram
{
    public static void main(String...args) throws Exception
    {
        SpringApplication application = new SpringApplication(MainProgram.class);
        application.run(args);
    }
}
