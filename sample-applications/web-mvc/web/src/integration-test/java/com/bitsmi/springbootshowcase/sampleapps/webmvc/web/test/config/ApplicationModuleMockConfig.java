package com.bitsmi.springbootshowcase.sampleapps.webmvc.web.test.config;

import com.bitsmi.springbootshowcase.sampleapps.application.common.UserRegistryApplicationService;
import com.bitsmi.springbootshowcase.sampleapps.application.common.UserRetrievalApplicationService;
import com.bitsmi.springbootshowcase.sampleapps.application.testsupport.common.CommonApplicationTestFixture;
import com.bitsmi.springbootshowcase.sampleapps.application.testsupport.common.UserRegistryApplicationServiceMocker;
import com.bitsmi.springbootshowcase.sampleapps.application.testsupport.common.UserRetrievalApplicationServiceMocker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@MockBean(UserRetrievalApplicationService.class)
@MockBean(UserRegistryApplicationService.class)
public class ApplicationModuleMockConfig
{
    /*-----------------------------*
     * COMMON
     *-----------------------------*/
    @Bean
    public UserRetrievalApplicationServiceMocker userRetrievalApplicationServiceMocker(
            UserRetrievalApplicationService userRetrievalApplicationService,
            @Autowired(required = false) CommonApplicationTestFixture commonApplicationTestFixture
    ) {
        return UserRetrievalApplicationServiceMocker.fromMockedInstance(userRetrievalApplicationService, commonApplicationTestFixture);
    }

    @Bean
    public UserRegistryApplicationServiceMocker userRegistryApplicationServiceMocker(
            UserRegistryApplicationService userRegistryApplicationService,
            @Autowired(required = false) CommonApplicationTestFixture commonApplicationTestFixture
    ) {
        return UserRegistryApplicationServiceMocker.fromMockedInstance(userRegistryApplicationService, commonApplicationTestFixture);
    }
}
