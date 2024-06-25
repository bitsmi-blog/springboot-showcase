package com.bitsmi.springbootshowcase.sampleapps.webmvc.web.test.config;

import com.bitsmi.springbootshowcase.sampleapps.application.common.UserCreationApplicationCommand;
import com.bitsmi.springbootshowcase.sampleapps.application.common.UserSummaryApplicationQuery;
import com.bitsmi.springbootshowcase.sampleapps.application.testsupport.common.CommonApplicationTestFixture;
import com.bitsmi.springbootshowcase.sampleapps.application.testsupport.common.UserSummaryApplicationQueryMocker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@MockBean(UserSummaryApplicationQuery.class)
@MockBean(UserCreationApplicationCommand.class)
public class ApplicationModuleMockConfig
{
    @Bean
    public UserSummaryApplicationQueryMocker userSummaryApplicationQueryMocker(
            UserSummaryApplicationQuery userSummaryApplicationQuery,
            @Autowired(required = false) CommonApplicationTestFixture commonApplicationTestFixture
    ) {
        return UserSummaryApplicationQueryMocker.fromMockedInstance(userSummaryApplicationQuery, commonApplicationTestFixture);
    }
}
