package com.bitsmi.springbootshowcase.sampleapps.webmvc.web.test.config;

import com.bitsmi.springbootshowcase.sampleapps.domain.common.UserDomainRepository;
import com.bitsmi.springbootshowcase.sampleapps.domain.testsupport.common.CommonDomainTestFixture;
import com.bitsmi.springbootshowcase.sampleapps.domain.testsupport.common.UserDomainRepositoryMocker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DomainModuleMockConfig {

    @Bean
    PasswordEncoder passwordEncoder()
    {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public UserDomainRepositoryMocker userQueriesDomainServiceMocker(
            UserDomainRepository userDomainRepository,
            @Autowired(required = false) CommonDomainTestFixture domainCommonTestFixture
    ) {
        return UserDomainRepositoryMocker.fromMockedInstance(userDomainRepository, domainCommonTestFixture);
    }
}
