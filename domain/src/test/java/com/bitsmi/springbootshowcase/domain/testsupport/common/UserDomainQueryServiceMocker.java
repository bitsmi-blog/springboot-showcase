package com.bitsmi.springbootshowcase.domain.testsupport.common;

import com.bitsmi.springbootshowcase.domain.common.IUserDomainQueryService;
import com.bitsmi.springbootshowcase.domain.common.model.User;
import com.bitsmi.springbootshowcase.domain.common.model.UserSummary;
import org.apache.commons.lang3.ObjectUtils;
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
    private final IDomainCommonTestScenario testScenario;

    private UserDomainQueryServiceMocker(IUserDomainQueryService serviceInstance, IDomainCommonTestScenario testScenario)
    {
        if(!Mockito.mockingDetails(serviceInstance).isMock()) {
            throw new IllegalArgumentException("Service instance must be a mock");
        }

        this.mockedService = serviceInstance;
        this.testScenario = testScenario;
    }

    public static UserDomainQueryServiceMocker mocker() {
        return mocker(null);
    }

    public static UserDomainQueryServiceMocker mocker(IDomainCommonTestScenario testScenario)
    {
        return new UserDomainQueryServiceMocker(
                mock(IUserDomainQueryService.class),
                ObjectUtils.defaultIfNull(testScenario, IDomainCommonTestScenario.getDefaultInstance())
        );
    }

    public static UserDomainQueryServiceMocker fromMockedInstance(IUserDomainQueryService serviceInstance)
    {
        return fromMockedInstance(serviceInstance, null);
    }

    public static UserDomainQueryServiceMocker fromMockedInstance(IUserDomainQueryService serviceInstance, IDomainCommonTestScenario testScenario)
    {
        return new UserDomainQueryServiceMocker(
                serviceInstance,
                ObjectUtils.defaultIfNull(testScenario, IDomainCommonTestScenario.getDefaultInstance())
        );
    }

    @BeforeTestExecution
    public void reset()
    {
        testScenario.configureUserDomainQueryServiceMocker(this);
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
