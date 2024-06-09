package com.bitsmi.springbootshowcase.sampleapps.webmvc.web.test.config;

import com.bitsmi.springbootshowcase.sampleapps.application.common.IUserCreationApplicationCommand;
import com.bitsmi.springbootshowcase.sampleapps.application.common.IUserSummaryApplicationQuery;
import com.bitsmi.springbootshowcase.sampleapps.application.testsupport.common.CommonApplicationTestFixture;
import com.bitsmi.springbootshowcase.sampleapps.application.testsupport.common.UserSummaryApplicationQueryMocker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@MockBean(IUserSummaryApplicationQuery.class)
@MockBean(IUserCreationApplicationCommand.class)
public class ApplicationModuleMockConfig
{
    @Bean
    public UserSummaryApplicationQueryMocker userSummaryApplicationQueryMocker(
            IUserSummaryApplicationQuery userSummaryApplicationQuery,
            @Autowired(required = false) CommonApplicationTestFixture applicationCommonTestFixture
    ) {
        return UserSummaryApplicationQueryMocker.fromMockedInstance(userSummaryApplicationQuery, applicationCommonTestFixture);
    }
}
