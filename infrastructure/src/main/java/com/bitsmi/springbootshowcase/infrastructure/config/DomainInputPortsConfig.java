package com.bitsmi.springbootshowcase.infrastructure.config;

import com.bitsmi.springbootshowcase.domain.common.IUserDomainCommandService;
import com.bitsmi.springbootshowcase.domain.common.IUserDomainQueryService;
import com.bitsmi.springbootshowcase.domain.common.impl.UserDomainCommandServiceImpl;
import com.bitsmi.springbootshowcase.domain.common.impl.UserDomainQueryServiceImpl;
import com.bitsmi.springbootshowcase.domain.common.spi.IUserGroupPersistenceService;
import com.bitsmi.springbootshowcase.domain.common.spi.IUserPersistenceService;
import com.bitsmi.springbootshowcase.domain.content.IItemSchemaDomainCommandService;
import com.bitsmi.springbootshowcase.domain.content.IItemSchemaDomainQueryService;
import com.bitsmi.springbootshowcase.domain.content.impl.ItemSchemaDomainCommandServiceImpl;
import com.bitsmi.springbootshowcase.domain.content.impl.ItemSchemaDomainQueryServiceImpl;
import com.bitsmi.springbootshowcase.domain.content.spi.IItemSchemaPersistenceService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DomainInputPortsConfig
{
    @Bean
    public IUserDomainQueryService userQueryDomainService(
            IUserPersistenceService userPersistenceService
    )
    {
        return new UserDomainQueryServiceImpl(userPersistenceService);
    }

    @Bean
    public IUserDomainCommandService userCommandDomainService(
            IUserPersistenceService userPersistenceService,
            IUserGroupPersistenceService userGroupPersistenceService,
            PasswordEncoder passwordEncoder
    )
    {
        return new UserDomainCommandServiceImpl(userPersistenceService, userGroupPersistenceService, passwordEncoder);
    }

    @Bean
    public IItemSchemaDomainQueryService itemSchemaQueryDomainService(IItemSchemaPersistenceService itemSchemaPersistenceService)
    {
        return new ItemSchemaDomainQueryServiceImpl(itemSchemaPersistenceService);
    }

    @Bean
    public IItemSchemaDomainCommandService itemSchemaCommandDomainService(IItemSchemaPersistenceService itemSchemaPersistenceService)
    {
        return new ItemSchemaDomainCommandServiceImpl(itemSchemaPersistenceService);
    }
}
