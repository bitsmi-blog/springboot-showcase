package com.bitsmi.springbootshowcase.sampleapps.web.test.config;

import com.bitsmi.springbootshowcase.sampleapps.domain.common.IUserDomainQueryService;
import com.bitsmi.springbootshowcase.sampleapps.domain.testsupport.common.IDomainCommonTestScenario;
import com.bitsmi.springbootshowcase.sampleapps.domain.testsupport.common.UserDomainQueryServiceMocker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@MockBean(IUserDomainQueryService.class)
public class DomainModuleMockConfig
{
    @Bean
    public UserDomainQueryServiceMocker userDomainQueryServiceMocker(
            IUserDomainQueryService userDomainQueryService,
            @Autowired(required = false) IDomainCommonTestScenario domainCommonTestScenario
    ) {
        return UserDomainQueryServiceMocker.fromMockedInstance(userDomainQueryService, domainCommonTestScenario);
    }
}
