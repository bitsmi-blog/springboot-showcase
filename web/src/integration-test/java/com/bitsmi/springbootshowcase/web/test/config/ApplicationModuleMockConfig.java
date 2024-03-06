package com.bitsmi.springbootshowcase.web.test.config;

import com.bitsmi.springbootshowcase.application.common.IUserCreationApplicationCommand;
import com.bitsmi.springbootshowcase.application.content.ICreateItemSchemaCommand;
import com.bitsmi.springbootshowcase.application.content.IRetrieveItemSchemaApplicationQuery;
import com.bitsmi.springbootshowcase.application.dummy.ISampleApplicationService;
import com.bitsmi.springbootshowcase.application.testsupport.content.CreateItemSchemaCommandMocker;
import com.bitsmi.springbootshowcase.application.testsupport.content.RetrieveItemSchemaApplicationQueryMocker;
import com.bitsmi.springbootshowcase.domain.common.spi.IUserPersistenceService;
import com.bitsmi.springbootshowcase.web.testsupport.application.dummy.SampleApplicationServiceMocker;
import com.bitsmi.springbootshowcase.domain.testsupport.common.UserPersistenceServiceMocker;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@MockBean(IUserPersistenceService.class)
@MockBean(ISampleApplicationService.class)
@MockBean(IRetrieveItemSchemaApplicationQuery.class)
@MockBean(ICreateItemSchemaCommand.class)
@MockBean(IUserCreationApplicationCommand.class)
public class ApplicationModuleMockConfig
{
    @Bean
    UserPersistenceServiceMocker userPersistenceService(IUserPersistenceService userPersistenceService)
    {
        return UserPersistenceServiceMocker.fromMockedInstance(userPersistenceService);
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
    CreateItemSchemaCommandMocker createItemSchemaCommandMocker(ICreateItemSchemaCommand createItemSchemaCommand)
    {
        return CreateItemSchemaCommandMocker.fromMockedInstance(createItemSchemaCommand);
    }
}
