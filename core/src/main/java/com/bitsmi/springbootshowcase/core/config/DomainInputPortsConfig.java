package com.bitsmi.springbootshowcase.core.config;

import com.bitsmi.springbootshowcase.domain.common.IUserCreationCommand;
import com.bitsmi.springbootshowcase.domain.common.impl.UserCreationCommandImpl;
import com.bitsmi.springbootshowcase.domain.common.spi.IUserPersistenceService;
import com.bitsmi.springbootshowcase.domain.content.IRetrieveItemSchemaQuery;
import com.bitsmi.springbootshowcase.domain.content.impl.RetrieveItemSchemaQueryImpl;
import com.bitsmi.springbootshowcase.domain.content.spi.IItemSchemaPersistenceService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DomainInputPortsConfig
{
    @Bean
    public IUserCreationCommand userCreationCommand(IUserPersistenceService userPersistenceService, PasswordEncoder passwordEncoder)
    {
        return new UserCreationCommandImpl(userPersistenceService, passwordEncoder);
    }

    @Bean
    public IRetrieveItemSchemaQuery retrieveItemSchemaQuery(IItemSchemaPersistenceService itemSchemaPersistenceService)
    {
        return new RetrieveItemSchemaQueryImpl(itemSchemaPersistenceService);
    }
}
