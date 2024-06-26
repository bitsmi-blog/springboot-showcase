package com.bitsmi.springbootshowcase.sampleapps.domain.common.config;

import com.bitsmi.springbootshowcase.sampleapps.domain.common.UserDomainFactory;
import com.bitsmi.springbootshowcase.sampleapps.domain.common.UserDomainQueryService;
import com.bitsmi.springbootshowcase.sampleapps.domain.common.impl.UserDomainFactoryImpl;
import com.bitsmi.springbootshowcase.sampleapps.domain.common.impl.UserDomainQueryServiceImpl;
import com.bitsmi.springbootshowcase.sampleapps.domain.common.UserGroupRepositoryService;
import com.bitsmi.springbootshowcase.sampleapps.domain.common.UserRepositoryService;
import org.springframework.security.crypto.password.PasswordEncoder;

public interface DomainConfig
{
    UserDomainQueryService userQueryDomainService(UserRepositoryService userRepositoryService);

    default UserDomainQueryService userDomainQueryServiceImpl(UserRepositoryService userRepositoryService)
    {
        return new UserDomainQueryServiceImpl(userRepositoryService);
    }

    UserDomainFactory userDomainFactory(
            UserRepositoryService userRepositoryService,
            UserGroupRepositoryService userGroupRepositoryService,
            PasswordEncoder passwordEncoder
    );

    default UserDomainFactory userDomainFactoryImpl(
            UserRepositoryService userRepositoryService,
            UserGroupRepositoryService userGroupRepositoryService,
            PasswordEncoder passwordEncoder
    ) {
        return new UserDomainFactoryImpl(userRepositoryService, userGroupRepositoryService, passwordEncoder);
    }

    UserRepositoryService userRepositoryService();
    UserGroupRepositoryService userGroupRepositoryService();
}
