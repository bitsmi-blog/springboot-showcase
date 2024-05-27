package com.bitsmi.springbootshowcase.sample.domain.testsupport.common;

import com.bitsmi.springbootshowcase.sample.domain.testsupport.common.model.UserSummaryTestDataBuilder;
import com.bitsmi.springbootshowcase.sample.domain.testsupport.common.model.UserTestDataBuilder;

public interface IDomainCommonTestScenario
{
    static IDomainCommonTestScenario getDefaultInstance()
    {
        return DefaultDomainCommonTestScenario.INSTANCE;
    }

    default void configureUserDomainQueryServiceMocker(UserDomainQueryServiceMocker mocker)
    {
        mocker.whenFindUserByUsernameGivenAnyUsernameThenReturnEmpty()
                .whenFindUserByUsernameThenReturnUser(UserTestDataBuilder.USERNAME_USER1, UserTestDataBuilder.user1())
                .whenFindUserSummaryByUsernameThenReturnUser(UserSummaryTestDataBuilder.USERNAME_USER1, UserSummaryTestDataBuilder.user1());
    }

    final class DefaultDomainCommonTestScenario implements IDomainCommonTestScenario
    {
        private static final DefaultDomainCommonTestScenario INSTANCE = new DefaultDomainCommonTestScenario();

        private DefaultDomainCommonTestScenario() { }
    }
}
