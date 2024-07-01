package com.bitsmi.springbootshowcase.sampleapps.webmvc.web.test.config;

import com.bitsmi.springbootshowcase.sampleapps.domain.common.UserQueriesDomainService;
import com.bitsmi.springbootshowcase.sampleapps.domain.testsupport.common.CommonDomainTestFixture;
import com.bitsmi.springbootshowcase.sampleapps.domain.testsupport.common.UserQueriesDomainServiceMocker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@MockBean(UserQueriesDomainService.class)
public class DomainModuleMockConfig
{
    @Bean
    PasswordEncoder passwordEncoder()
    {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public UserQueriesDomainServiceMocker userQueriesDomainServiceMocker(
            UserQueriesDomainService userQueriesDomainService,
            @Autowired(required = false) CommonDomainTestFixture domainCommonTestFixture
    ) {
        return UserQueriesDomainServiceMocker.fromMockedInstance(userQueriesDomainService, domainCommonTestFixture);
    }
}
