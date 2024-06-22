package com.bitsmi.springbootshowcase.sampleapps.infrastructure.config;

import com.bitsmi.springbootshowcase.sampleapps.domain.common.UserDomainCommandService;
import com.bitsmi.springbootshowcase.sampleapps.domain.common.UserDomainQueryService;
import com.bitsmi.springbootshowcase.sampleapps.domain.common.impl.UserDomainCommandServiceImpl;
import com.bitsmi.springbootshowcase.sampleapps.domain.common.impl.UserDomainQueryServiceImpl;
import com.bitsmi.springbootshowcase.sampleapps.domain.common.spi.UserGroupRepositoryService;
import com.bitsmi.springbootshowcase.sampleapps.domain.common.spi.UserRepositoryService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DomainInputPortsConfig
{
    @Bean
    public UserDomainQueryService userQueryDomainService(
            UserRepositoryService userRepositoryService
    )
    {
        return new UserDomainQueryServiceImpl(userRepositoryService);
    }

    @Bean
    public UserDomainCommandService userCommandDomainService(
            UserRepositoryService userRepositoryService,
            UserGroupRepositoryService userGroupRepositoryService,
            PasswordEncoder passwordEncoder
    )
    {
        return new UserDomainCommandServiceImpl(userRepositoryService, userGroupRepositoryService, passwordEncoder);
    }
}
