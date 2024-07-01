package com.bitsmi.springbootshowcase.sampleapps.domain.testsupport.common;

import com.bitsmi.springbootshowcase.sampleapps.domain.common.UserQueriesDomainService;
import com.bitsmi.springbootshowcase.sampleapps.domain.common.model.User;
import com.bitsmi.springbootshowcase.sampleapps.domain.common.model.UserSummary;
import org.apache.commons.lang3.ObjectUtils;
import org.mockito.Mockito;
import org.springframework.test.context.event.annotation.BeforeTestExecution;

import java.util.Optional;
import java.util.function.Consumer;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public final class UserQueriesDomainServiceMocker
{
    private final UserQueriesDomainService mockedService;
    private final CommonDomainTestFixture testFixture;

    private UserQueriesDomainServiceMocker(UserQueriesDomainService serviceInstance, CommonDomainTestFixture testFixture)
    {
        if(!Mockito.mockingDetails(serviceInstance).isMock()) {
            throw new IllegalArgumentException("Service instance must be a mock");
        }

        this.mockedService = serviceInstance;
        this.testFixture = testFixture;
    }

    public static UserQueriesDomainServiceMocker mocker() {
        return mocker(null);
    }

    public static UserQueriesDomainServiceMocker mocker(CommonDomainTestFixture testFixture)
    {
        return new UserQueriesDomainServiceMocker(
                mock(UserQueriesDomainService.class),
                ObjectUtils.defaultIfNull(testFixture, CommonDomainTestFixture.getDefaultInstance())
        );
    }

    public static UserQueriesDomainServiceMocker fromMockedInstance(UserQueriesDomainService serviceInstance)
    {
        return fromMockedInstance(serviceInstance, null);
    }

    public static UserQueriesDomainServiceMocker fromMockedInstance(UserQueriesDomainService serviceInstance, CommonDomainTestFixture testScenario)
    {
        return new UserQueriesDomainServiceMocker(
                serviceInstance,
                ObjectUtils.defaultIfNull(testScenario, CommonDomainTestFixture.getDefaultInstance())
        );
    }

    @BeforeTestExecution
    public void reset()
    {
        testFixture.configureUserQueriesDomainServiceMocker(this);
    }

    public UserQueriesDomainServiceMocker configureMock(Consumer<UserQueriesDomainService> mockConsumer)
    {
        mockConsumer.accept(mockedService);
        return this;
    }

    public UserQueriesDomainServiceMocker whenFindUserByUsernameGivenAnyUsernameThenReturnEmpty()
    {
        when(mockedService.findUserByUsername(any()))
                .thenReturn(Optional.empty());
        return this;
    }

    public UserQueriesDomainServiceMocker whenFindUserByUsernameThenReturnUser(String userName, User result)
    {
        when(mockedService.findUserByUsername(userName))
                .thenReturn(Optional.ofNullable(result));
        return this;
    }

    public UserQueriesDomainServiceMocker whenFindUserSummaryByUsernameThenReturnUser(String userName, UserSummary result)
    {
        when(mockedService.findUserSummaryByUsername(userName))
                .thenReturn(Optional.ofNullable(result));
        return this;
    }
}
