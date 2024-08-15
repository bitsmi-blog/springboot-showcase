package com.bitsmi.springbootshowcase.sampleapps.application.testsupport.common;

import com.bitsmi.springbootshowcase.sampleapps.application.common.UserRetrievalApplicationService;
import com.bitsmi.springbootshowcase.sampleapps.domain.common.dto.PaginatedData;
import com.bitsmi.springbootshowcase.sampleapps.domain.common.dto.Pagination;
import com.bitsmi.springbootshowcase.sampleapps.domain.common.model.User;
import org.apache.commons.lang3.ObjectUtils;
import org.mockito.Mockito;
import org.springframework.test.context.event.annotation.BeforeTestExecution;

import java.util.Optional;
import java.util.function.Consumer;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserRetrievalApplicationServiceMocker
{
    private final UserRetrievalApplicationService mockedService;
    private final CommonApplicationTestFixture testFixture;

    private UserRetrievalApplicationServiceMocker(UserRetrievalApplicationService mockedService, CommonApplicationTestFixture testFixture)
    {
        if(!Mockito.mockingDetails(mockedService).isMock()) {
            throw new IllegalArgumentException("Service instance must be a mock");
        }

        this.mockedService = mockedService;
        this.testFixture = testFixture;
    }

    public static UserRetrievalApplicationServiceMocker mocker()
    {
        return mocker(null);
    }

    public static UserRetrievalApplicationServiceMocker mocker(CommonApplicationTestFixture testFixture)
    {
        return new UserRetrievalApplicationServiceMocker(
                mock(UserRetrievalApplicationService.class),
                ObjectUtils.defaultIfNull(testFixture, CommonApplicationTestFixture.getDefaultInstance())
        );
    }

    public static UserRetrievalApplicationServiceMocker fromMockedInstance(UserRetrievalApplicationService serviceInstance)
    {
        return fromMockedInstance(serviceInstance, null);
    }

    public static UserRetrievalApplicationServiceMocker fromMockedInstance(UserRetrievalApplicationService serviceInstance, CommonApplicationTestFixture testFixture)
    {
        return new UserRetrievalApplicationServiceMocker(
                serviceInstance,
                ObjectUtils.defaultIfNull(testFixture, CommonApplicationTestFixture.getDefaultInstance())
        );
    }

    @BeforeTestExecution
    public void reset()
    {
        testFixture.configureUserRetrievalApplicationServiceMocker(this);
    }

    public UserRetrievalApplicationServiceMocker configureMock(Consumer<UserRetrievalApplicationService> mockConsumer)
    {
        mockConsumer.accept(mockedService);
        return this;
    }

    public UserRetrievalApplicationServiceMocker whenFindAllUsersByPaginationThenReturnPaginatedData(
            Pagination pagination,
            PaginatedData<User> paginatedData
    ) {
        when(mockedService.findAllUsers(pagination))
                .thenReturn(paginatedData);

        return this;
    }

    public UserRetrievalApplicationServiceMocker whenFindUserByIdThenReturnInstance(Long id, User instance)
    {
        when(mockedService.findUserById(id))
                .thenReturn(Optional.of(instance));

        return this;
    }

    public UserRetrievalApplicationServiceMocker whenFindUserByIdThenReturnEmpty(Long id)
    {
        when(mockedService.findUserById(id))
                .thenReturn(Optional.empty());

        return this;
    }
}
