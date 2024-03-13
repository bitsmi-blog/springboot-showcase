package com.bitsmi.springbootshowcase.application.testsupport.common;

import com.bitsmi.springbootshowcase.application.common.IUserSummaryApplicationQuery;
import com.bitsmi.springbootshowcase.domain.common.model.UserSummary;
import com.bitsmi.springbootshowcase.domain.testsupport.common.model.UserSummaryTestDataBuilder;
import org.mockito.Mockito;
import org.springframework.test.context.event.annotation.BeforeTestExecution;

import java.util.Optional;
import java.util.function.Consumer;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserSummaryApplicationQueryMocker
{
    private final IUserSummaryApplicationQuery mockedQuery;

    private UserSummaryApplicationQueryMocker(IUserSummaryApplicationQuery queryInstance)
    {
        if(!Mockito.mockingDetails(queryInstance).isMock()) {
            throw new IllegalArgumentException("Query instance must be a mock");
        }

        this.mockedQuery = queryInstance;
    }

    public static UserSummaryApplicationQueryMocker mocker()
    {
        return new UserSummaryApplicationQueryMocker(mock(IUserSummaryApplicationQuery.class));
    }

    public static UserSummaryApplicationQueryMocker fromMockedInstance(IUserSummaryApplicationQuery queryInstance)
    {
        return new UserSummaryApplicationQueryMocker(queryInstance);
    }

    @BeforeTestExecution
    public void reset()
    {
        this.whenFindUserSummaryByUsernameGivenAnyUsernameThenReturnEmpty()
                .whenFindUserSummaryByUsernameThenReturnUserSummary(UserSummaryTestDataBuilder.USERNAME_USER1, UserSummaryTestDataBuilder.user1());
    }

    public UserSummaryApplicationQueryMocker configureMock(Consumer<IUserSummaryApplicationQuery> mockConsumer)
    {
        mockConsumer.accept(mockedQuery);
        return this;
    }

    public UserSummaryApplicationQueryMocker whenFindUserSummaryByUsernameGivenAnyUsernameThenReturnEmpty()
    {
        when(mockedQuery.findUserSummaryByUsername(any()))
                .thenReturn(Optional.empty());
        return this;
    }

    public UserSummaryApplicationQueryMocker whenFindUserSummaryByUsernameThenReturnUserSummary(String userName, UserSummary result)
    {
        when(mockedQuery.findUserSummaryByUsername(userName))
                .thenReturn(Optional.ofNullable(result));
        return this;
    }
}
