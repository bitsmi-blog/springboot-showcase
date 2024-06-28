package com.bitsmi.springbootshowcase.sampleapps.domain.testsupport.common;

import com.bitsmi.springbootshowcase.sampleapps.domain.common.UserDomainRepository;
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

public class UserDomainRepositoryMocker
{
    private final UserDomainRepository mockedRepository;
    private final CommonDomainTestFixture testFixture;

    private UserDomainRepositoryMocker(UserDomainRepository repositoryInstance, CommonDomainTestFixture testFixture)
    {
        if(!Mockito.mockingDetails(repositoryInstance).isMock()) {
            throw new IllegalArgumentException("Repository instance must be a mock");
        }

        this.mockedRepository = repositoryInstance;
        this.testFixture = testFixture;
    }

    public static UserDomainRepositoryMocker mocker() {
        return mocker(null);
    }

    public static UserDomainRepositoryMocker mocker(CommonDomainTestFixture testFixture)
    {
        return new UserDomainRepositoryMocker(
                mock(UserDomainRepository.class),
                ObjectUtils.defaultIfNull(testFixture, CommonDomainTestFixture.getDefaultInstance())
        );
    }

    public static UserDomainRepositoryMocker fromMockedInstance(UserDomainRepository repositoryInstance)
    {
        return fromMockedInstance(repositoryInstance, null);
    }

    public static UserDomainRepositoryMocker fromMockedInstance(UserDomainRepository repositoryInstance, CommonDomainTestFixture testScenario)
    {
        return new UserDomainRepositoryMocker(
                repositoryInstance,
                ObjectUtils.defaultIfNull(testScenario, CommonDomainTestFixture.getDefaultInstance())
        );
    }

    @BeforeTestExecution
    public void reset()
    {
        testFixture.configureUserDomainRepositoryMocker(this);
    }

    public UserDomainRepositoryMocker configureMock(Consumer<UserDomainRepository> mockConsumer)
    {
        mockConsumer.accept(mockedRepository);
        return this;
    }

    public UserDomainRepositoryMocker whenCountAllUsersThenReturnNumber(Long result)
    {
        when(mockedRepository.countAllUsers())
                .thenReturn(result);
        return this;
    }

    public UserDomainRepositoryMocker whenFindUserByUsernameGivenAnyUsernameThenReturnEmpty()
    {
        when(mockedRepository.findUserByUsername(any()))
                .thenReturn(Optional.empty());
        return this;
    }

    public UserDomainRepositoryMocker whenFindUserByUsernameThenReturnUser(String userName, User result)
    {
        when(mockedRepository.findUserByUsername(userName))
                .thenReturn(Optional.ofNullable(result));
        return this;
    }

    public UserDomainRepositoryMocker whenFindUserSummaryByUsernameThenReturnUser(String userName, UserSummary result)
    {
        when(mockedRepository.findUserSummaryByUsername(userName))
                .thenReturn(Optional.ofNullable(result));
        return this;
    }

    public UserDomainRepositoryMocker whenCreateUserThenReturnUser(User givenUser, User result)
    {
        when(mockedRepository.createUser(givenUser))
                .thenReturn(result);
        return this;
    }
}
