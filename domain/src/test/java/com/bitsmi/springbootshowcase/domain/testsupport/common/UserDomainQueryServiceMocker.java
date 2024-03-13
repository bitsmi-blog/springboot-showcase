package com.bitsmi.springbootshowcase.domain.testsupport.common;

import com.bitsmi.springbootshowcase.domain.common.IUserDomainQueryService;
import com.bitsmi.springbootshowcase.domain.common.model.User;
import com.bitsmi.springbootshowcase.domain.common.model.UserSummary;
import com.bitsmi.springbootshowcase.domain.testsupport.common.model.UserSummaryTestDataBuilder;
import com.bitsmi.springbootshowcase.domain.testsupport.common.model.UserTestDataBuilder;
import org.mockito.Mockito;
import org.springframework.test.context.event.annotation.BeforeTestExecution;

import java.util.Optional;
import java.util.function.Consumer;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserDomainQueryServiceMocker
{
    private final IUserDomainQueryService mockedService;

    private UserDomainQueryServiceMocker(IUserDomainQueryService serviceInstance)
    {
        if(!Mockito.mockingDetails(serviceInstance).isMock()) {
            throw new IllegalArgumentException("Service instance must be a mock");
        }

        this.mockedService = serviceInstance;
    }

    public static UserDomainQueryServiceMocker mocker()
    {
        return new UserDomainQueryServiceMocker(mock(IUserDomainQueryService.class));
    }

    public static UserDomainQueryServiceMocker fromMockedInstance(IUserDomainQueryService serviceInstance)
    {
        return new UserDomainQueryServiceMocker(serviceInstance);
    }

    @BeforeTestExecution
    public void reset()
    {
        this.whenFindUserByUsernameGivenAnyUsernameThenReturnEmpty()
                .whenFindUserByUsernameThenReturnUser(UserTestDataBuilder.USERNAME_USER1, UserTestDataBuilder.user1())
                .whenFindUserSummaryByUsernameThenReturnUser(UserSummaryTestDataBuilder.USERNAME_USER1, UserSummaryTestDataBuilder.user1());
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
