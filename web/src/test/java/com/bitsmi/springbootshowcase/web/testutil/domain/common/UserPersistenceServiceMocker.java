package com.bitsmi.springbootshowcase.web.testutil.domain.common;

import com.bitsmi.springbootshowcase.domain.common.model.User;
import com.bitsmi.springbootshowcase.domain.common.model.UserSummary;
import com.bitsmi.springbootshowcase.domain.common.spi.IUserPersistenceService;
import com.bitsmi.springbootshowcase.domain.testutil.shared.common.model.UserSummaryTestDataBuilder;
import com.bitsmi.springbootshowcase.domain.testutil.shared.common.model.UserTestDataBuilder;
import org.mockito.Mockito;
import org.springframework.test.context.event.annotation.BeforeTestExecution;

import java.util.Optional;
import java.util.function.Consumer;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserPersistenceServiceMocker
{
    private final IUserPersistenceService mockedService;

    private UserPersistenceServiceMocker(IUserPersistenceService serviceInstance)
    {
        if(!Mockito.mockingDetails(serviceInstance).isMock()) {
            throw new IllegalArgumentException("Service instance must be a mock");
        }

        this.mockedService = serviceInstance;
    }

    public static UserPersistenceServiceMocker mocker()
    {
        return new UserPersistenceServiceMocker(mock(IUserPersistenceService.class));
    }

    public static UserPersistenceServiceMocker fromMockedInstance(IUserPersistenceService serviceInstance)
    {
        return new UserPersistenceServiceMocker(serviceInstance);
    }

    @BeforeTestExecution
    public void reset()
    {
        this
                .whenFindUserByUsernameGivenAnyUsernameThenReturnEmpty()
                .whenFindUserByUsernameThenReturnUser(UserTestDataBuilder.USERNAME_USER1, UserTestDataBuilder.user1())
                .whenFindUserSummaryByUsernameThenReturnUser(UserTestDataBuilder.USERNAME_USER1, UserSummaryTestDataBuilder.user1());
    }

    public UserPersistenceServiceMocker configureMock(Consumer<IUserPersistenceService> mockConsumer)
    {
        mockConsumer.accept(mockedService);
        return this;
    }

    public UserPersistenceServiceMocker whenFindUserByUsernameGivenAnyUsernameThenReturnEmpty()
    {
        when(mockedService.findUserByUsername(any()))
                .thenReturn(Optional.empty());
        return this;
    }

    public UserPersistenceServiceMocker whenFindUserByUsernameThenReturnUser(String userName, User result)
    {
        when(mockedService.findUserByUsername(userName))
                .thenReturn(Optional.ofNullable(result));
        return this;
    }

    public UserPersistenceServiceMocker whenFindUserSummaryByUsernameThenReturnUser(String userName, UserSummary result)
    {
        when(mockedService.findUserSummaryByUsername(userName))
                .thenReturn(Optional.ofNullable(result));
        return this;
    }
}
