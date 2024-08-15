package com.bitsmi.springbootshowcase.sampleapps.application.testsupport.common;

import com.bitsmi.springbootshowcase.sampleapps.domain.common.dto.PaginatedData;
import com.bitsmi.springbootshowcase.sampleapps.domain.common.dto.Pagination;
import com.bitsmi.springbootshowcase.sampleapps.domain.common.dto.Sort;
import com.bitsmi.springbootshowcase.sampleapps.domain.common.model.User;
import com.bitsmi.springbootshowcase.sampleapps.domain.testsupport.common.model.UserObjectMother;

import java.util.List;

public interface CommonApplicationTestFixture
{
    static CommonApplicationTestFixture getDefaultInstance()
    {
        return DefaultCommonApplicationTestFixture.INSTANCE;
    }

    default void configureUserRetrievalApplicationServiceMocker(UserRetrievalApplicationServiceMocker mocker)
    {
        whenFindAllUsersByPaginationThenReturnPaginatedData(mocker);
        mocker.whenFindUserByIdThenReturnInstance(UserObjectMother.AN_ADMIN_USER.id(), UserObjectMother.anAdminUser());
        mocker.whenFindUserByIdThenReturnInstance(UserObjectMother.A_NORMAL_USER.id(), UserObjectMother.aNormalUser());
    }

    private void whenFindAllUsersByPaginationThenReturnPaginatedData(UserRetrievalApplicationServiceMocker mocker)
    {
        Pagination pagination = Pagination.of(0, 10, Sort.UNSORTED);
        mocker.whenFindAllUsersByPaginationThenReturnPaginatedData(
                pagination,
                PaginatedData.<User>builder()
                        .data(
                                List.of(
                                        UserObjectMother.anAdminUser(),
                                        UserObjectMother.aNormalUser()
                                )
                        )
                        .pagination(pagination)
                        .pageCount(2)
                        .totalPages(1)
                        .totalCount(2)
                        .totalPages(1)
                        .build()
        );
    }

    default void configureUserRegistryApplicationServiceMocker(UserRegistryApplicationServiceMocker mocker)
    {
        mocker.whenCreateUserThenReturnUser(UserObjectMother.aNonExistingUser());
        mocker.whenUpdateUserThenReturnUser(UserObjectMother.A_NORMAL_USER.id(), UserObjectMother.aNormalUser());
    }

    final class DefaultCommonApplicationTestFixture implements CommonApplicationTestFixture
    {
        private static final DefaultCommonApplicationTestFixture INSTANCE = new DefaultCommonApplicationTestFixture();

        private DefaultCommonApplicationTestFixture() { }
    }
}
