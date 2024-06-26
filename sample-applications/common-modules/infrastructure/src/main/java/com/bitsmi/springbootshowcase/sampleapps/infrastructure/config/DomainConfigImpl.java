package com.bitsmi.springbootshowcase.sampleapps.infrastructure.config;

import com.bitsmi.springbootshowcase.sampleapps.domain.common.UserDomainFactory;
import com.bitsmi.springbootshowcase.sampleapps.domain.common.UserDomainQueryService;
import com.bitsmi.springbootshowcase.sampleapps.domain.common.UserGroupRepositoryService;
import com.bitsmi.springbootshowcase.sampleapps.domain.common.UserRepositoryService;
import com.bitsmi.springbootshowcase.sampleapps.domain.common.config.DomainConfig;
import com.bitsmi.springbootshowcase.sampleapps.infrastructure.common.config.CommonMapperDependencies;
import com.bitsmi.springbootshowcase.sampleapps.infrastructure.common.config.CommonRepositoryDependencies;
import com.bitsmi.springbootshowcase.sampleapps.infrastructure.common.impl.UserGroupRepositoryServiceImpl;
import com.bitsmi.springbootshowcase.sampleapps.infrastructure.common.impl.UserRepositoryServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DomainConfigImpl implements DomainConfig
{
    private final CommonRepositoryDependencies commonRepositoryDependencies;
    private final CommonMapperDependencies commonMapperDependencies;

    public DomainConfigImpl(
            CommonRepositoryDependencies commonRepositoryDependencies,
            CommonMapperDependencies commonMapperDependencies
    ) {
        this.commonRepositoryDependencies = commonRepositoryDependencies;
        this.commonMapperDependencies = commonMapperDependencies;
    }

    @Bean
    @Override
    public UserDomainQueryService userQueryDomainService(UserRepositoryService userRepositoryService)
    {
        return userDomainQueryServiceImpl(userRepositoryService);
    }

    @Bean
    @Override
    public UserDomainFactory userDomainFactory(
            UserRepositoryService userRepositoryService,
            UserGroupRepositoryService userGroupRepositoryService,
            PasswordEncoder passwordEncoder
    )
    {
        return userDomainFactoryImpl(userRepositoryService, userGroupRepositoryService, passwordEncoder);
    }

    @Bean
    @Override
    public UserRepositoryService userRepositoryService()
    {
        return new UserRepositoryServiceImpl(
                commonRepositoryDependencies.getUserRepository(),
                commonRepositoryDependencies.getUserGroupRepository(),
                commonMapperDependencies.getUserModelMapper(),
                commonMapperDependencies.getUserSummaryModelMapper()
        );
    }

    @Bean
    @Override
    public UserGroupRepositoryService userGroupRepositoryService()
    {
        return new UserGroupRepositoryServiceImpl(
                commonRepositoryDependencies.getUserGroupRepository(),
                commonMapperDependencies.getUserGroupModelMapper()
        );
    }
}
