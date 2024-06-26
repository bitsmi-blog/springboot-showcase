package com.bitsmi.springbootshowcase.sampleapps.domain.testsupport.common;

import com.bitsmi.springbootshowcase.sampleapps.domain.common.UserRepositoryService;
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

public class UserRepositoryServiceMocker
{
    private final UserRepositoryService mockedService;
    private final CommonDomainTestFixture testFixture;

    private UserRepositoryServiceMocker(UserRepositoryService serviceInstance, CommonDomainTestFixture testFixture)
    {
        if(!Mockito.mockingDetails(serviceInstance).isMock()) {
            throw new IllegalArgumentException("Service instance must be a mock");
        }

        this.mockedService = serviceInstance;
        this.testFixture = testFixture;
    }

    public static UserRepositoryServiceMocker mocker() {
        return mocker(null);
    }

    public static UserRepositoryServiceMocker mocker(CommonDomainTestFixture testFixture)
    {
        return new UserRepositoryServiceMocker(
                mock(UserRepositoryService.class),
                ObjectUtils.defaultIfNull(testFixture, CommonDomainTestFixture.getDefaultInstance())
        );
    }

    public static UserRepositoryServiceMocker fromMockedInstance(UserRepositoryService serviceInstance)
    {
        return fromMockedInstance(serviceInstance, null);
    }

    public static UserRepositoryServiceMocker fromMockedInstance(UserRepositoryService serviceInstance, CommonDomainTestFixture testScenario)
    {
        return new UserRepositoryServiceMocker(
                serviceInstance,
                ObjectUtils.defaultIfNull(testScenario, CommonDomainTestFixture.getDefaultInstance())
        );
    }

    @BeforeTestExecution
    public void reset()
    {
        testFixture.configureUserRepositoryServiceMocker(this);
    }

    public UserRepositoryServiceMocker configureMock(Consumer<UserRepositoryService> mockConsumer)
    {
        mockConsumer.accept(mockedService);
        return this;
    }

    public UserRepositoryServiceMocker whenCountAllUsersThenReturnNumber(Long result)
    {
        when(mockedService.countAllUsers())
                .thenReturn(result);
        return this;
    }

    public UserRepositoryServiceMocker whenFindUserByUsernameGivenAnyUsernameThenReturnEmpty()
    {
        when(mockedService.findUserByUsername(any()))
                .thenReturn(Optional.empty());
        return this;
    }

    public UserRepositoryServiceMocker whenFindUserByUsernameThenReturnUser(String userName, User result)
    {
        when(mockedService.findUserByUsername(userName))
                .thenReturn(Optional.ofNullable(result));
        return this;
    }

    public UserRepositoryServiceMocker whenFindUserSummaryByUsernameThenReturnUser(String userName, UserSummary result)
    {
        when(mockedService.findUserSummaryByUsername(userName))
                .thenReturn(Optional.ofNullable(result));
        return this;
    }

    public UserRepositoryServiceMocker whenCreateUserThenReturnUser(User givenUser, User result)
    {
        when(mockedService.createUser(givenUser))
                .thenReturn(result);
        return this;
    }
}
