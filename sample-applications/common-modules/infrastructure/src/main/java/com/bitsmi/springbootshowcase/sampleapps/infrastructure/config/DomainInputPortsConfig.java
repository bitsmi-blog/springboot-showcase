package com.bitsmi.springbootshowcase.sampleapps.infrastructure.config;

import com.bitsmi.springbootshowcase.sampleapps.domain.common.IUserDomainCommandService;
import com.bitsmi.springbootshowcase.sampleapps.domain.common.IUserDomainQueryService;
import com.bitsmi.springbootshowcase.sampleapps.domain.common.impl.UserDomainCommandServiceImpl;
import com.bitsmi.springbootshowcase.sampleapps.domain.common.impl.UserDomainQueryServiceImpl;
import com.bitsmi.springbootshowcase.sampleapps.domain.common.spi.IUserGroupRepositoryService;
import com.bitsmi.springbootshowcase.sampleapps.domain.common.spi.IUserRepositoryService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DomainInputPortsConfig
{
    @Bean
    public IUserDomainQueryService userQueryDomainService(
            IUserRepositoryService userRepositoryService
    )
    {
        return new UserDomainQueryServiceImpl(userRepositoryService);
    }

    @Bean
    public IUserDomainCommandService userCommandDomainService(
            IUserRepositoryService userRepositoryService,
            IUserGroupRepositoryService userGroupRepositoryService,
            PasswordEncoder passwordEncoder
    )
    {
        return new UserDomainCommandServiceImpl(userRepositoryService, userGroupRepositoryService, passwordEncoder);
    }
}
