package com.bitsmi.springbootshowcase.core.config;

import com.bitsmi.springbootshowcase.domain.common.IUserCreationCommand;
import com.bitsmi.springbootshowcase.domain.common.impl.UserCreationCommandImpl;
import com.bitsmi.springbootshowcase.domain.common.spi.IUserPersistenceService;
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
}
