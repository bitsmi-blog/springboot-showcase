package com.bitsmi.springbootshowcase.sampleapps.application.testsupport.common;

import com.bitsmi.springbootshowcase.sampleapps.application.common.UserSummaryApplicationQuery;
import com.bitsmi.springbootshowcase.sampleapps.domain.common.model.UserSummary;
import org.apache.commons.lang3.ObjectUtils;
import org.mockito.Mockito;
import org.springframework.test.context.event.annotation.BeforeTestExecution;

import java.util.Optional;
import java.util.function.Consumer;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public final class UserSummaryApplicationQueryMocker
{
    private final UserSummaryApplicationQuery mockedQuery;
    private final CommonApplicationTestFixture testFixture;

    private UserSummaryApplicationQueryMocker(UserSummaryApplicationQuery queryInstance, CommonApplicationTestFixture testFixture)
    {
        if(!Mockito.mockingDetails(queryInstance).isMock()) {
            throw new IllegalArgumentException("Query instance must be a mock");
        }

        this.mockedQuery = queryInstance;
        this.testFixture = testFixture;
    }

    public static UserSummaryApplicationQueryMocker mocker()
    {
        return mocker(null);
    }

    public static UserSummaryApplicationQueryMocker mocker(CommonApplicationTestFixture testFixture)
    {
        return new UserSummaryApplicationQueryMocker(
                mock(UserSummaryApplicationQuery.class),
                ObjectUtils.defaultIfNull(testFixture, CommonApplicationTestFixture.getDefaultInstance())
        );
    }

    public static UserSummaryApplicationQueryMocker fromMockedInstance(UserSummaryApplicationQuery queryInstance)
    {
        return fromMockedInstance(queryInstance, null);
    }

    public static UserSummaryApplicationQueryMocker fromMockedInstance(UserSummaryApplicationQuery queryInstance, CommonApplicationTestFixture testFixture)
    {
        return new UserSummaryApplicationQueryMocker(
                queryInstance,
                ObjectUtils.defaultIfNull(testFixture, CommonApplicationTestFixture.getDefaultInstance())
        );
    }

    @BeforeTestExecution
    public void reset()
    {
        testFixture.configureUserDomainQueryServiceMocker(this);
    }

    public UserSummaryApplicationQueryMocker configureMock(Consumer<UserSummaryApplicationQuery> mockConsumer)
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
