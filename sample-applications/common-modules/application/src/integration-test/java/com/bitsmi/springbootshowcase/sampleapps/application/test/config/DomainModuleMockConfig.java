package com.bitsmi.springbootshowcase.sampleapps.application.test.config;

import com.bitsmi.springbootshowcase.sampleapps.domain.common.UserDomainFactory;
import com.bitsmi.springbootshowcase.sampleapps.domain.common.UserDomainQueryService;
import com.bitsmi.springbootshowcase.sampleapps.domain.common.UserRepositoryService;
import com.bitsmi.springbootshowcase.sampleapps.domain.testsupport.common.UserDomainQueryServiceMocker;
import com.bitsmi.springbootshowcase.sampleapps.domain.testsupport.common.UserRepositoryServiceMocker;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@MockBean(UserDomainQueryService.class)
@MockBean(UserDomainFactory.class)
@MockBean(UserRepositoryService.class)
public class DomainModuleMockConfig
{
    @Bean
    public UserDomainQueryServiceMocker userDomainQueryServiceMocker(UserDomainQueryService userDomainQueryService)
    {
        return UserDomainQueryServiceMocker.fromMockedInstance(userDomainQueryService);
    }

    @Bean
    public UserRepositoryServiceMocker userRepositoryServiceMocker(UserRepositoryService userRepositoryService)
    {
        return UserRepositoryServiceMocker.fromMockedInstance(userRepositoryService);
    }
}
