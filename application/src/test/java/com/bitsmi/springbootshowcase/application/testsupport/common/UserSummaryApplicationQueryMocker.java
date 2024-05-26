package com.bitsmi.springbootshowcase.application.testsupport.common;

import com.bitsmi.springbootshowcase.application.common.IUserSummaryApplicationQuery;
import com.bitsmi.springbootshowcase.domain.common.model.UserSummary;
import org.apache.commons.lang3.ObjectUtils;
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
    private final IApplicationCommonTestScenario testScenario;

    private UserSummaryApplicationQueryMocker(IUserSummaryApplicationQuery queryInstance, IApplicationCommonTestScenario testScenario)
    {
        if(!Mockito.mockingDetails(queryInstance).isMock()) {
            throw new IllegalArgumentException("Query instance must be a mock");
        }

        this.mockedQuery = queryInstance;
        this.testScenario = testScenario;
    }

    public static UserSummaryApplicationQueryMocker mocker()
    {
        return mocker(null);
    }

    public static UserSummaryApplicationQueryMocker mocker(IApplicationCommonTestScenario testScenario)
    {
        return new UserSummaryApplicationQueryMocker(
                mock(IUserSummaryApplicationQuery.class),
                ObjectUtils.defaultIfNull(testScenario, IApplicationCommonTestScenario.getDefaultInstance())
        );
    }

    public static UserSummaryApplicationQueryMocker fromMockedInstance(IUserSummaryApplicationQuery queryInstance)
    {
        return fromMockedInstance(queryInstance, null);
    }

    public static UserSummaryApplicationQueryMocker fromMockedInstance(IUserSummaryApplicationQuery queryInstance, IApplicationCommonTestScenario testScenario)
    {
        return new UserSummaryApplicationQueryMocker(
                queryInstance,
                ObjectUtils.defaultIfNull(testScenario, IApplicationCommonTestScenario.getDefaultInstance())
        );
    }

    @BeforeTestExecution
    public void reset()
    {
        testScenario.configureUserDomainQueryServiceMocker(this);
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
