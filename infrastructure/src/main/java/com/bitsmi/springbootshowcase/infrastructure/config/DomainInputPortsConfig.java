package com.bitsmi.springbootshowcase.infrastructure.config;

import com.bitsmi.springbootshowcase.domain.common.IUserDomainCommandService;
import com.bitsmi.springbootshowcase.domain.common.IUserDomainQueryService;
import com.bitsmi.springbootshowcase.domain.common.impl.UserDomainCommandServiceImpl;
import com.bitsmi.springbootshowcase.domain.common.impl.UserDomainQueryServiceImpl;
import com.bitsmi.springbootshowcase.domain.common.spi.IUserGroupRepositoryService;
import com.bitsmi.springbootshowcase.domain.common.spi.IUserRepositoryService;
import com.bitsmi.springbootshowcase.domain.content.IItemSchemaDomainCommandService;
import com.bitsmi.springbootshowcase.domain.content.IItemSchemaDomainQueryService;
import com.bitsmi.springbootshowcase.domain.content.impl.ItemSchemaDomainCommandServiceImpl;
import com.bitsmi.springbootshowcase.domain.content.impl.ItemSchemaDomainQueryServiceImpl;
import com.bitsmi.springbootshowcase.domain.content.spi.IItemSchemaRepositoryService;
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

    @Bean
    public IItemSchemaDomainQueryService itemSchemaQueryDomainService(
        IItemSchemaRepositoryService itemSchemaRepositoryService)
    {
        return new ItemSchemaDomainQueryServiceImpl(itemSchemaRepositoryService);
    }

    @Bean
    public IItemSchemaDomainCommandService itemSchemaCommandDomainService(
        IItemSchemaRepositoryService itemSchemaRepositoryService)
    {
        return new ItemSchemaDomainCommandServiceImpl(itemSchemaRepositoryService);
    }
}
