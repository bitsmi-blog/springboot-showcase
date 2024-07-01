package com.bitsmi.springbootshowcase.sampleapps.application.testsupport.common;

import com.bitsmi.springbootshowcase.sampleapps.domain.testsupport.common.model.UserSummaryObjectMother;

public interface CommonApplicationTestFixture
{
    static CommonApplicationTestFixture getDefaultInstance()
    {
        return DefaultCommonApplicationTestFixture.INSTANCE;
    }

    default void configureUserSummaryApplicationServiceMocker(UserSummaryApplicationServiceMocker mocker)
    {
        mocker.whenFindUserSummaryByUsernameGivenAnyUsernameThenReturnEmpty()
                .whenFindUserSummaryByUsernameThenReturnUserSummary(UserSummaryObjectMother.USERNAME_USER1, UserSummaryObjectMother.user1());
    }

    final class DefaultCommonApplicationTestFixture implements CommonApplicationTestFixture
    {
        private static final DefaultCommonApplicationTestFixture INSTANCE = new DefaultCommonApplicationTestFixture();

        private DefaultCommonApplicationTestFixture() { }
    }
}
