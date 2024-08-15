package com.bitsmi.springbootshowcase.sampleapps.domain.common.config;

import com.bitsmi.springbootshowcase.sampleapps.domain.common.UserDomainFactory;
import com.bitsmi.springbootshowcase.sampleapps.domain.common.UserDomainRepository;
import com.bitsmi.springbootshowcase.sampleapps.domain.common.UserGroupDomainRepository;
import com.bitsmi.springbootshowcase.sampleapps.domain.common.impl.UserDomainFactoryImpl;
import org.springframework.security.crypto.password.PasswordEncoder;

public interface DomainConfig
{
    UserDomainFactory userDomainFactory(
            UserGroupDomainRepository userGroupRepositoryService,
            PasswordEncoder passwordEncoder
    );

    default UserDomainFactory userDomainFactoryImpl(
            UserGroupDomainRepository userGroupDomainRepository,
            PasswordEncoder passwordEncoder
    ) {
        return new UserDomainFactoryImpl(userGroupDomainRepository, passwordEncoder);
    }

    UserDomainRepository userDomainRepository();
    UserGroupDomainRepository userGroupDomainRepository();
}
