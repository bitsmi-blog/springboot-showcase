package com.bitsmi.springbootshowcase.sampleapps.application.testsupport.common;

import com.bitsmi.springbootshowcase.sampleapps.application.common.UserRegistryApplicationService;
import com.bitsmi.springbootshowcase.sampleapps.domain.common.exception.ElementAlreadyExistsException;
import com.bitsmi.springbootshowcase.sampleapps.domain.common.exception.ElementNotFoundException;
import com.bitsmi.springbootshowcase.sampleapps.domain.common.model.User;
import org.apache.commons.lang3.ObjectUtils;
import org.mockito.Mockito;
import org.springframework.test.context.event.annotation.BeforeTestExecution;

import java.util.function.Consumer;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserRegistryApplicationServiceMocker
{
    private final UserRegistryApplicationService mockedService;
    private final CommonApplicationTestFixture testFixture;

    private UserRegistryApplicationServiceMocker(UserRegistryApplicationService mockedService, CommonApplicationTestFixture testFixture)
    {
        if(!Mockito.mockingDetails(mockedService).isMock()) {
            throw new IllegalArgumentException("Service instance must be a mock");
        }

        this.mockedService = mockedService;
        this.testFixture = testFixture;
    }

    public static UserRegistryApplicationServiceMocker mocker()
    {
        return mocker(null);
    }

    public static UserRegistryApplicationServiceMocker mocker(CommonApplicationTestFixture testFixture)
    {
        return new UserRegistryApplicationServiceMocker(
                mock(UserRegistryApplicationService.class),
                ObjectUtils.defaultIfNull(testFixture, CommonApplicationTestFixture.getDefaultInstance())
        );
    }

    public static UserRegistryApplicationServiceMocker fromMockedInstance(UserRegistryApplicationService serviceInstance)
    {
        return fromMockedInstance(serviceInstance, null);
    }

    public static UserRegistryApplicationServiceMocker fromMockedInstance(UserRegistryApplicationService serviceInstance, CommonApplicationTestFixture testFixture)
    {
        return new UserRegistryApplicationServiceMocker(
                serviceInstance,
                ObjectUtils.defaultIfNull(testFixture, CommonApplicationTestFixture.getDefaultInstance())
        );
    }

    @BeforeTestExecution
    public void reset()
    {
        testFixture.configureUserRegistryApplicationServiceMocker(this);
    }

    public UserRegistryApplicationServiceMocker configureMock(Consumer<UserRegistryApplicationService> mockConsumer)
    {
        mockConsumer.accept(mockedService);
        return this;
    }

    public UserRegistryApplicationServiceMocker whenCreateUserThenReturnUser(User user) {
        when(mockedService.createUser(
                argThat(arg -> user.username().equals(arg)),
                any(),
                any(),
                any())
        )
                .thenReturn(user);

        return this;
    }

    public UserRegistryApplicationServiceMocker whenCreateUserWithUsernameThenThrowElementAlreadyExistsException(String username) {
        when(mockedService.createUser(
                argThat(username::equals),
                any(),
                any(),
                any())
        )
                .thenThrow(new ElementAlreadyExistsException(
                                User.class.getName(),
                                "User with username (%s) already exists".formatted(username)
                        )
                );

        return this;
    }

    public UserRegistryApplicationServiceMocker whenUpdateUserThenReturnUser(Long id, User user) {
        when(mockedService.updateUser(
                argThat(id::equals),
                any(),
                any(),
                any())
        )
                .thenReturn(user);

        return this;
    }

    public UserRegistryApplicationServiceMocker whenUpdateUserWithIdThenThrowElementNotFoundExistsException(Long id) {
        when(mockedService.updateUser(
                argThat(id::equals),
                any(),
                any(),
                any())
        )
                .thenThrow(new ElementNotFoundException(
                                User.class.getName(),
                                "User with id (%s) not found".formatted(id)
                        )
                );

        return this;
    }

    public UserRegistryApplicationServiceMocker whenChangeUserPasswordThenThrowElementNotFoundException(Long id)
    {
        doThrow(new ElementNotFoundException(User.class.getName(), "User with id (%s) not found".formatted(id)))
                .when(mockedService).changeUserPassword(eq(id), any());

        return this;
    }
}
