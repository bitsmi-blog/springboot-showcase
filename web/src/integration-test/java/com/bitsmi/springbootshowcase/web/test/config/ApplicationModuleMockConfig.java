package com.bitsmi.springbootshowcase.web.test.config;

import com.bitsmi.springbootshowcase.application.common.IUserCreationApplicationCommand;
import com.bitsmi.springbootshowcase.application.common.IUserSummaryApplicationQuery;
import com.bitsmi.springbootshowcase.application.content.ICreateItemSchemaApplicationCommand;
import com.bitsmi.springbootshowcase.application.content.IRetrieveItemSchemaApplicationQuery;
import com.bitsmi.springbootshowcase.application.dummy.ISampleApplicationService;
import com.bitsmi.springbootshowcase.application.testsupport.common.UserSummaryApplicationQueryMocker;
import com.bitsmi.springbootshowcase.application.testsupport.content.CreateItemSchemaCommandMocker;
import com.bitsmi.springbootshowcase.application.testsupport.content.RetrieveItemSchemaApplicationQueryMocker;
import com.bitsmi.springbootshowcase.web.testsupport.application.dummy.SampleApplicationServiceMocker;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@MockBean(IUserSummaryApplicationQuery.class)
@MockBean(ISampleApplicationService.class)
@MockBean(IRetrieveItemSchemaApplicationQuery.class)
@MockBean(ICreateItemSchemaApplicationCommand.class)
@MockBean(IUserCreationApplicationCommand.class)
public class ApplicationModuleMockConfig
{
    @Bean
    public UserSummaryApplicationQueryMocker userSummaryApplicationQueryMocker(IUserSummaryApplicationQuery userSummaryApplicationQuery)
    {
        return UserSummaryApplicationQueryMocker.fromMockedInstance(userSummaryApplicationQuery);
    }

    @Bean
    SampleApplicationServiceMocker sampleApplicationServiceMocker(ISampleApplicationService sampleApplicationService)
    {
        return SampleApplicationServiceMocker.fromMockedInstance(sampleApplicationService);
    }

    @Bean
    RetrieveItemSchemaApplicationQueryMocker retrieveItemSchemaApplicationQueryMocker(IRetrieveItemSchemaApplicationQuery retrieveItemSchemaApplicationQuery)
    {
        return RetrieveItemSchemaApplicationQueryMocker.fromMockedInstance(retrieveItemSchemaApplicationQuery);
    }

    @Bean
    CreateItemSchemaCommandMocker createItemSchemaCommandMocker(ICreateItemSchemaApplicationCommand createItemSchemaCommand)
    {
        return CreateItemSchemaCommandMocker.fromMockedInstance(createItemSchemaCommand);
    }
}
