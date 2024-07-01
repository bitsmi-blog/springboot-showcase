package com.bitsmi.springbootshowcase.sampleapps.webmvc.web.test.config;

import com.bitsmi.springbootshowcase.sampleapps.application.common.UserCreationApplicationService;
import com.bitsmi.springbootshowcase.sampleapps.application.common.UserSummaryApplicationService;
import com.bitsmi.springbootshowcase.sampleapps.application.testsupport.common.CommonApplicationTestFixture;
import com.bitsmi.springbootshowcase.sampleapps.application.testsupport.common.UserSummaryApplicationServiceMocker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@MockBean(UserSummaryApplicationService.class)
@MockBean(UserCreationApplicationService.class)
public class ApplicationModuleMockConfig
{
    @Bean
    public UserSummaryApplicationServiceMocker userSummaryApplicationServiceMocker(
            UserSummaryApplicationService userSummaryApplicationService,
            @Autowired(required = false) CommonApplicationTestFixture commonApplicationTestFixture
    ) {
        return UserSummaryApplicationServiceMocker.fromMockedInstance(userSummaryApplicationService, commonApplicationTestFixture);
    }
}
