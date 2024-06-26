package com.bitsmi.springbootshowcase.sampleapps.domain.testsupport.common;

import com.bitsmi.springbootshowcase.sampleapps.domain.common.model.User;
import com.bitsmi.springbootshowcase.sampleapps.domain.testsupport.common.model.UserObjectMother;
import com.bitsmi.springbootshowcase.sampleapps.domain.testsupport.common.model.UserSummaryObjectMother;

public interface CommonDomainTestFixture
{
    static CommonDomainTestFixture getDefaultInstance()
    {
        return DefaultCommonDomainTestFixture.INSTANCE;
    }

    default void configureUserDomainQueryServiceMocker(UserDomainQueryServiceMocker mocker)
    {
        mocker.whenFindUserByUsernameGivenAnyUsernameThenReturnEmpty()
                .whenFindUserByUsernameThenReturnUser(UserObjectMother.USERNAME_USER1, UserObjectMother.user1())
                .whenFindUserSummaryByUsernameThenReturnUser(UserSummaryObjectMother.USERNAME_USER1, UserSummaryObjectMother.user1());
    }

    default void configureUserRepositoryServiceMocker(UserRepositoryServiceMocker mocker)
    {
        final User user1 = UserObjectMother.user1();
        mocker.whenFindUserByUsernameGivenAnyUsernameThenReturnEmpty()
                .whenCountAllUsersThenReturnNumber(2L)
                .whenFindUserByUsernameThenReturnUser(UserObjectMother.USERNAME_USER1, user1)
                .whenFindUserSummaryByUsernameThenReturnUser(UserSummaryObjectMother.USERNAME_USER1, UserSummaryObjectMother.user1())
                .whenCreateUserThenReturnUser(user1, user1);
    }

    final class DefaultCommonDomainTestFixture implements CommonDomainTestFixture
    {
        private static final DefaultCommonDomainTestFixture INSTANCE = new DefaultCommonDomainTestFixture();

        private DefaultCommonDomainTestFixture() { }
    }
}
