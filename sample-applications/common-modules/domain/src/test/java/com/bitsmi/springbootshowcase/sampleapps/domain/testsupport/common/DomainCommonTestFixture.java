package com.bitsmi.springbootshowcase.sampleapps.domain.testsupport.common;

import com.bitsmi.springbootshowcase.sampleapps.domain.testsupport.common.model.UserSummaryObjectMother;
import com.bitsmi.springbootshowcase.sampleapps.domain.testsupport.common.model.UserObjectMother;

public interface DomainCommonTestFixture
{
    static DomainCommonTestFixture getDefaultInstance()
    {
        return DefaultDomainCommonTestFixture.INSTANCE;
    }

    default void configureUserDomainQueryServiceMocker(UserDomainQueryServiceMocker mocker)
    {
        mocker.whenFindUserByUsernameGivenAnyUsernameThenReturnEmpty()
                .whenFindUserByUsernameThenReturnUser(UserObjectMother.USERNAME_USER1, UserObjectMother.user1())
                .whenFindUserSummaryByUsernameThenReturnUser(UserSummaryObjectMother.USERNAME_USER1, UserSummaryObjectMother.user1());
    }

    final class DefaultDomainCommonTestFixture implements DomainCommonTestFixture
    {
        private static final DefaultDomainCommonTestFixture INSTANCE = new DefaultDomainCommonTestFixture();

        private DefaultDomainCommonTestFixture() { }
    }
}
