package com.bitsmi.springbootshowcase.sampleapps.webmvc.web.testsupport.internal;

import com.bitsmi.springbootshowcase.sampleapps.webmvc.web.WebModulePackage;
import com.bitsmi.springbootshowcase.sampleapps.webmvc.web.config.WebModuleConfig;
import com.bitsmi.springbootshowcase.sampleapps.webmvc.web.test.config.ApplicationModuleMockConfig;
import com.bitsmi.springbootshowcase.sampleapps.webmvc.web.test.config.DomainModuleMockConfig;
import com.bitsmi.springbootshowcase.sampleapps.webmvc.web.test.config.UserDetailsTestConfig;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@TestConfiguration
@ComponentScan(basePackageClasses = WebModulePackage.class)
@Import({
        WebModuleConfig.class,
        ApplicationModuleMockConfig.class,
        DomainModuleMockConfig.class,
        UserDetailsTestConfig.class
})
public class ControllerIntegrationTestConfig {

}
