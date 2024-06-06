package com.bitsmi.springbootshowcase.sampleapps.application.testsupport.common;

import com.bitsmi.springbootshowcase.sampleapps.domain.testsupport.common.model.UserSummaryObjectMother;

public interface ApplicationCommonTestFixture
{
    static ApplicationCommonTestFixture getDefaultInstance()
    {
        return DefaultApplicationCommonTestFixture.INSTANCE;
    }

    default void configureUserDomainQueryServiceMocker(UserSummaryApplicationQueryMocker mocker)
    {
        mocker.whenFindUserSummaryByUsernameGivenAnyUsernameThenReturnEmpty()
                .whenFindUserSummaryByUsernameThenReturnUserSummary(UserSummaryObjectMother.USERNAME_USER1, UserSummaryObjectMother.user1());
    }

    final class DefaultApplicationCommonTestFixture implements ApplicationCommonTestFixture
    {
        private static final DefaultApplicationCommonTestFixture INSTANCE = new DefaultApplicationCommonTestFixture();

        private DefaultApplicationCommonTestFixture() { }
    }
}
