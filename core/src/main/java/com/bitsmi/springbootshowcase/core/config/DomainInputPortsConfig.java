package com.bitsmi.springbootshowcase.core.config;

import com.bitsmi.springbootshowcase.domain.common.IUserCommandDomainService;
import com.bitsmi.springbootshowcase.domain.common.impl.UserCommandDomainServiceImpl;
import com.bitsmi.springbootshowcase.domain.common.spi.IUserPersistenceService;
import com.bitsmi.springbootshowcase.domain.content.IItemSchemaQueryDomainService;
import com.bitsmi.springbootshowcase.domain.content.impl.ItemSchemaQueryDomainServiceImpl;
import com.bitsmi.springbootshowcase.domain.content.spi.IItemSchemaPersistenceService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DomainInputPortsConfig
{
    @Bean
    public IUserCommandDomainService userCreationCommand(IUserPersistenceService userPersistenceService, PasswordEncoder passwordEncoder)
    {
        return new UserCommandDomainServiceImpl(userPersistenceService, passwordEncoder);
    }

    @Bean
    public IItemSchemaQueryDomainService retrieveItemSchemaQuery(IItemSchemaPersistenceService itemSchemaPersistenceService)
    {
        return new ItemSchemaQueryDomainServiceImpl(itemSchemaPersistenceService);
    }
}
