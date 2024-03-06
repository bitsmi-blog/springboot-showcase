package com.bitsmi.springbootshowcase.infrastructure.config;

import com.bitsmi.springbootshowcase.domain.common.IUserCommandDomainService;
import com.bitsmi.springbootshowcase.domain.common.impl.UserCommandDomainServiceImpl;
import com.bitsmi.springbootshowcase.domain.common.spi.IUserPersistenceService;
import com.bitsmi.springbootshowcase.domain.content.IItemSchemaCommandDomainService;
import com.bitsmi.springbootshowcase.domain.content.IItemSchemaQueryDomainService;
import com.bitsmi.springbootshowcase.domain.content.impl.ItemSchemaCommandDomainServiceImpl;
import com.bitsmi.springbootshowcase.domain.content.impl.ItemSchemaQueryDomainServiceImpl;
import com.bitsmi.springbootshowcase.domain.content.spi.IItemSchemaPersistenceService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DomainInputPortsConfig
{
    @Bean
    public IUserCommandDomainService userCommandDomainService(IUserPersistenceService userPersistenceService, PasswordEncoder passwordEncoder)
    {
        return new UserCommandDomainServiceImpl(userPersistenceService, passwordEncoder);
    }

    @Bean
    public IItemSchemaQueryDomainService itemSchemaQueryDomainService(IItemSchemaPersistenceService itemSchemaPersistenceService)
    {
        return new ItemSchemaQueryDomainServiceImpl(itemSchemaPersistenceService);
    }

    @Bean
    public IItemSchemaCommandDomainService itemSchemaCommandDomainService(IItemSchemaPersistenceService itemSchemaPersistenceService)
    {
        return new ItemSchemaCommandDomainServiceImpl(itemSchemaPersistenceService);
    }
}
