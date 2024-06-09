package com.bitsmi.springbootshowcase.sampleapps.domain.testsupport.common;

import com.bitsmi.springbootshowcase.sampleapps.domain.common.IUserDomainQueryService;
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

public final class UserDomainQueryServiceMocker
{
    private final IUserDomainQueryService mockedService;
    private final CommonDomainTestFixture testFixture;

    private UserDomainQueryServiceMocker(IUserDomainQueryService serviceInstance, CommonDomainTestFixture testFixture)
    {
        if(!Mockito.mockingDetails(serviceInstance).isMock()) {
            throw new IllegalArgumentException("Service instance must be a mock");
        }

        this.mockedService = serviceInstance;
        this.testFixture = testFixture;
    }

    public static UserDomainQueryServiceMocker mocker() {
        return mocker(null);
    }

    public static UserDomainQueryServiceMocker mocker(CommonDomainTestFixture testFixture)
    {
        return new UserDomainQueryServiceMocker(
                mock(IUserDomainQueryService.class),
                ObjectUtils.defaultIfNull(testFixture, CommonDomainTestFixture.getDefaultInstance())
        );
    }

    public static UserDomainQueryServiceMocker fromMockedInstance(IUserDomainQueryService serviceInstance)
    {
        return fromMockedInstance(serviceInstance, null);
    }

    public static UserDomainQueryServiceMocker fromMockedInstance(IUserDomainQueryService serviceInstance, CommonDomainTestFixture testScenario)
    {
        return new UserDomainQueryServiceMocker(
                serviceInstance,
                ObjectUtils.defaultIfNull(testScenario, CommonDomainTestFixture.getDefaultInstance())
        );
    }

    @BeforeTestExecution
    public void reset()
    {
        testFixture.configureUserDomainQueryServiceMocker(this);
    }

    public UserDomainQueryServiceMocker configureMock(Consumer<IUserDomainQueryService> mockConsumer)
    {
        mockConsumer.accept(mockedService);
        return this;
    }

    public UserDomainQueryServiceMocker whenFindUserByUsernameGivenAnyUsernameThenReturnEmpty()
    {
        when(mockedService.findUserByUsername(any()))
                .thenReturn(Optional.empty());
        return this;
    }

    public UserDomainQueryServiceMocker whenFindUserByUsernameThenReturnUser(String userName, User result)
    {
        when(mockedService.findUserByUsername(userName))
                .thenReturn(Optional.ofNullable(result));
        return this;
    }

    public UserDomainQueryServiceMocker whenFindUserSummaryByUsernameThenReturnUser(String userName, UserSummary result)
    {
        when(mockedService.findUserSummaryByUsername(userName))
                .thenReturn(Optional.ofNullable(result));
        return this;
    }
}
