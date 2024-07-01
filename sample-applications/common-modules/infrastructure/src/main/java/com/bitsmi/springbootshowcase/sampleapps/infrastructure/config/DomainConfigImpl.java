package com.bitsmi.springbootshowcase.sampleapps.infrastructure.config;

import com.bitsmi.springbootshowcase.sampleapps.domain.common.UserDomainFactory;
import com.bitsmi.springbootshowcase.sampleapps.domain.common.UserQueriesDomainService;
import com.bitsmi.springbootshowcase.sampleapps.domain.common.UserGroupDomainRepository;
import com.bitsmi.springbootshowcase.sampleapps.domain.common.UserDomainRepository;
import com.bitsmi.springbootshowcase.sampleapps.domain.common.config.DomainConfig;
import com.bitsmi.springbootshowcase.sampleapps.infrastructure.common.config.CommonMapperDependencies;
import com.bitsmi.springbootshowcase.sampleapps.infrastructure.common.config.CommonRepositoryDependencies;
import com.bitsmi.springbootshowcase.sampleapps.infrastructure.common.impl.UserGroupDomainRepositoryImpl;
import com.bitsmi.springbootshowcase.sampleapps.infrastructure.common.impl.UserDomainRepositoryImpl;
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
    public UserQueriesDomainService userQueriesDomainService(UserDomainRepository userRepositoryService)
    {
        return userQueriesDomainServiceImpl(userRepositoryService);
    }

    @Bean
    @Override
    public UserDomainFactory userDomainFactory(
            UserDomainRepository userRepositoryService,
            UserGroupDomainRepository userGroupRepositoryService,
            PasswordEncoder passwordEncoder
    )
    {
        return userDomainFactoryImpl(userRepositoryService, userGroupRepositoryService, passwordEncoder);
    }

    @Bean
    @Override
    public UserDomainRepository userRepositoryService()
    {
        return new UserDomainRepositoryImpl(
                commonRepositoryDependencies.getUserRepository(),
                commonRepositoryDependencies.getUserGroupRepository(),
                commonMapperDependencies.getUserModelMapper(),
                commonMapperDependencies.getUserSummaryModelMapper()
        );
    }

    @Bean
    @Override
    public UserGroupDomainRepository userGroupRepositoryService()
    {
        return new UserGroupDomainRepositoryImpl(
                commonRepositoryDependencies.getUserGroupRepository(),
                commonMapperDependencies.getUserGroupModelMapper()
        );
    }
}
