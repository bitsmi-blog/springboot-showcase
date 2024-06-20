package com.bitsmi.springbootshowcase.sampleapps.webmvc.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

// @SpringBootApplication == { @Configuration @EnableAutoConfiguration @ComponentScan }
@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackageClasses = { MainPackage.class })
public class MainProgram
{
    public static void main(String...args) throws Exception
    {
        SpringApplication application = new SpringApplication(MainProgram.class);
        application.run(args);
    }
}
