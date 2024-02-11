package com.bitsmi.springbootshowcase.web.test.config;

import com.bitsmi.springbootshowcase.domain.common.spi.IUserPersistenceService;
import com.bitsmi.springbootshowcase.web.testutil.domain.common.UserPersistenceServiceMocker;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@MockBean(IUserPersistenceService.class)
public class ServiceMockConfig
{
    @Bean
    UserPersistenceServiceMocker userPersistenceService(IUserPersistenceService userPersistenceService)
    {
        return UserPersistenceServiceMocker.fromMockedInstance(userPersistenceService);
    }
}
