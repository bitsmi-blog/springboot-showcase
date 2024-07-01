package com.bitsmi.springbootshowcase.sampleapps.application.testsupport.common;

import com.bitsmi.springbootshowcase.sampleapps.application.common.UserSummaryApplicationService;
import com.bitsmi.springbootshowcase.sampleapps.domain.common.model.UserSummary;
import org.apache.commons.lang3.ObjectUtils;
import org.mockito.Mockito;
import org.springframework.test.context.event.annotation.BeforeTestExecution;

import java.util.Optional;
import java.util.function.Consumer;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public final class UserSummaryApplicationServiceMocker
{
    private final UserSummaryApplicationService mockedService;
    private final CommonApplicationTestFixture testFixture;

    private UserSummaryApplicationServiceMocker(UserSummaryApplicationService serviceInstance, CommonApplicationTestFixture testFixture)
    {
        if(!Mockito.mockingDetails(serviceInstance).isMock()) {
            throw new IllegalArgumentException("Query instance must be a mock");
        }

        this.mockedService = serviceInstance;
        this.testFixture = testFixture;
    }

    public static UserSummaryApplicationServiceMocker mocker()
    {
        return mocker(null);
    }

    public static UserSummaryApplicationServiceMocker mocker(CommonApplicationTestFixture testFixture)
    {
        return new UserSummaryApplicationServiceMocker(
                mock(UserSummaryApplicationService.class),
                ObjectUtils.defaultIfNull(testFixture, CommonApplicationTestFixture.getDefaultInstance())
        );
    }

    public static UserSummaryApplicationServiceMocker fromMockedInstance(UserSummaryApplicationService queryInstance)
    {
        return fromMockedInstance(queryInstance, null);
    }

    public static UserSummaryApplicationServiceMocker fromMockedInstance(UserSummaryApplicationService queryInstance, CommonApplicationTestFixture testFixture)
    {
        return new UserSummaryApplicationServiceMocker(
                queryInstance,
                ObjectUtils.defaultIfNull(testFixture, CommonApplicationTestFixture.getDefaultInstance())
        );
    }

    @BeforeTestExecution
    public void reset()
    {
        testFixture.configureUserSummaryApplicationServiceMocker(this);
    }

    public UserSummaryApplicationServiceMocker configureMock(Consumer<UserSummaryApplicationService> mockConsumer)
    {
        mockConsumer.accept(mockedService);
        return this;
    }

    public UserSummaryApplicationServiceMocker whenFindUserSummaryByUsernameGivenAnyUsernameThenReturnEmpty()
    {
        when(mockedService.findUserSummaryByUsername(any()))
                .thenReturn(Optional.empty());
        return this;
    }

    public UserSummaryApplicationServiceMocker whenFindUserSummaryByUsernameThenReturnUserSummary(String userName, UserSummary result)
    {
        when(mockedService.findUserSummaryByUsername(userName))
                .thenReturn(Optional.ofNullable(result));
        return this;
    }
}
