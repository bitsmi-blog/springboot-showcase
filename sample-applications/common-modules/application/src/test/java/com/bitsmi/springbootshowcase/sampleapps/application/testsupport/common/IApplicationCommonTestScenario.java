package com.bitsmi.springbootshowcase.sampleapps.application.testsupport.common;

import com.bitsmi.springbootshowcase.sampleapps.domain.testsupport.common.model.UserSummaryTestDataBuilder;

public interface IApplicationCommonTestScenario
{
    static IApplicationCommonTestScenario getDefaultInstance()
    {
        return DefaultApplicationCommonTestScenario.INSTANCE;
    }

    default void configureUserDomainQueryServiceMocker(UserSummaryApplicationQueryMocker mocker)
    {
        mocker.whenFindUserSummaryByUsernameGivenAnyUsernameThenReturnEmpty()
                .whenFindUserSummaryByUsernameThenReturnUserSummary(UserSummaryTestDataBuilder.USERNAME_USER1, UserSummaryTestDataBuilder.user1());
    }

    final class DefaultApplicationCommonTestScenario implements IApplicationCommonTestScenario
    {
        private static final DefaultApplicationCommonTestScenario INSTANCE = new DefaultApplicationCommonTestScenario();

        private DefaultApplicationCommonTestScenario() { }
    }
}
