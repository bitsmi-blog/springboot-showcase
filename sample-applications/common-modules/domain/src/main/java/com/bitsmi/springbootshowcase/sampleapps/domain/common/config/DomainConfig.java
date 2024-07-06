package com.bitsmi.springbootshowcase.sampleapps.domain.common.config;

import com.bitsmi.springbootshowcase.sampleapps.domain.common.UserDomainFactory;
import com.bitsmi.springbootshowcase.sampleapps.domain.common.UserQueriesDomainService;
import com.bitsmi.springbootshowcase.sampleapps.domain.common.impl.UserDomainFactoryImpl;
import com.bitsmi.springbootshowcase.sampleapps.domain.common.impl.UserQueriesDomainServiceImpl;
import com.bitsmi.springbootshowcase.sampleapps.domain.common.UserGroupDomainRepository;
import com.bitsmi.springbootshowcase.sampleapps.domain.common.UserDomainRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

public interface DomainConfig
{
    UserQueriesDomainService userQueriesDomainService(UserDomainRepository userRepositoryService);

    default UserQueriesDomainService userQueriesDomainServiceImpl(UserDomainRepository userDomainRepository)
    {
        return new UserQueriesDomainServiceImpl(userDomainRepository);
    }

    UserDomainFactory userDomainFactory(
            UserDomainRepository userRepositoryService,
            UserGroupDomainRepository userGroupRepositoryService,
            PasswordEncoder passwordEncoder
    );

    default UserDomainFactory userDomainFactoryImpl(
            UserDomainRepository userDomainRepository,
            UserGroupDomainRepository UserGroupDomainRepository,
            PasswordEncoder passwordEncoder
    ) {
        return new UserDomainFactoryImpl(userDomainRepository, UserGroupDomainRepository, passwordEncoder);
    }

    UserDomainRepository userDomainRepository();
    UserGroupDomainRepository userGroupDomainRepository();
}
