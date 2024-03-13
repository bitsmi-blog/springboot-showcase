package com.bitsmi.springbootshowcase.application.test.config;

import com.bitsmi.springbootshowcase.domain.common.IUserDomainCommandService;
import com.bitsmi.springbootshowcase.domain.common.IUserDomainQueryService;
import com.bitsmi.springbootshowcase.domain.content.IItemSchemaDomainCommandService;
import com.bitsmi.springbootshowcase.domain.content.IItemSchemaDomainQueryService;
import com.bitsmi.springbootshowcase.domain.testsupport.common.UserDomainQueryServiceMocker;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@MockBean(IUserDomainQueryService.class)
@MockBean(IUserDomainCommandService.class)
@MockBean(IItemSchemaDomainQueryService.class)
@MockBean(IItemSchemaDomainCommandService.class)
public class DomainModuleMockConfig
{
    @Bean
    public UserDomainQueryServiceMocker userDomainQueryServiceMocker(IUserDomainQueryService userDomainQueryService)
    {
        return UserDomainQueryServiceMocker.fromMockedInstance(userDomainQueryService);
    }
}
