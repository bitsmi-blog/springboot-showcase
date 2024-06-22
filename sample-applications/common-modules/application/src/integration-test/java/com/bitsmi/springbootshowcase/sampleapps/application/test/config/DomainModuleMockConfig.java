package com.bitsmi.springbootshowcase.sampleapps.application.test.config;

import com.bitsmi.springbootshowcase.sampleapps.domain.common.UserDomainCommandService;
import com.bitsmi.springbootshowcase.sampleapps.domain.common.UserDomainQueryService;
import com.bitsmi.springbootshowcase.sampleapps.domain.testsupport.common.UserDomainQueryServiceMocker;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@MockBean(UserDomainQueryService.class)
@MockBean(UserDomainCommandService.class)
public class DomainModuleMockConfig
{
    @Bean
    public UserDomainQueryServiceMocker userDomainQueryServiceMocker(UserDomainQueryService userDomainQueryService)
    {
        return UserDomainQueryServiceMocker.fromMockedInstance(userDomainQueryService);
    }
}
