package com.bitsmi.springbootshowcase.sampleapps.domain.testsupport.common;

import com.bitsmi.springbootshowcase.sampleapps.domain.common.model.User;
import com.bitsmi.springbootshowcase.sampleapps.domain.testsupport.common.model.UserObjectMother;

public interface CommonDomainTestFixture
{
    static CommonDomainTestFixture getDefaultInstance()
    {
        return DefaultCommonDomainTestFixture.INSTANCE;
    }

    default void configureUserDomainRepositoryMocker(UserDomainRepositoryMocker mocker)
    {
        final User user1 = UserObjectMother.anAdminUser();
        mocker.whenFindUserByUsernameGivenAnyUsernameThenReturnEmpty()
                .whenCountAllUsersThenReturnNumber(2L)
                .whenFindUserByUsernameThenReturnUser(user1.username(), user1)
                .whenCreateUserThenReturnUser(user1, user1);
    }

    final class DefaultCommonDomainTestFixture implements CommonDomainTestFixture
    {
        private static final DefaultCommonDomainTestFixture INSTANCE = new DefaultCommonDomainTestFixture();

        private DefaultCommonDomainTestFixture() { }
    }
}
