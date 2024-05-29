package com.bitsmi.springbootshowcase.sampleapps.application.test.config;

import com.bitsmi.springbootshowcase.sampleapps.domain.common.IUserDomainCommandService;
import com.bitsmi.springbootshowcase.sampleapps.domain.common.IUserDomainQueryService;
import com.bitsmi.springbootshowcase.sampleapps.domain.testsupport.common.UserDomainQueryServiceMocker;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@MockBean(IUserDomainQueryService.class)
@MockBean(IUserDomainCommandService.class)
public class DomainModuleMockConfig
{
    @Bean
    public UserDomainQueryServiceMocker userDomainQueryServiceMocker(IUserDomainQueryService userDomainQueryService)
    {
        return UserDomainQueryServiceMocker.fromMockedInstance(userDomainQueryService);
    }
}
