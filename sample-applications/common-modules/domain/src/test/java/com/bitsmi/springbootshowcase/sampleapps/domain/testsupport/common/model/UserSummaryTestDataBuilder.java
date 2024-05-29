package com.bitsmi.springbootshowcase.sampleapps.domain.testsupport.common.model;

import com.bitsmi.springbootshowcase.sampleapps.domain.common.model.UserSummary;

public class UserSummaryTestDataBuilder
{
    public static final String USERNAME_USER1 = "john.doe";

    public static UserSummary user1()
    {
        return builder()
                .user1()
                .build();
    }

    public static Builder builder()
    {
        return new UserSummaryTestDataBuilder.Builder();
    }

    public static final class Builder
    {
        private final UserSummary.Builder delegate = UserSummary.builder();

        public Builder user1()
        {
            delegate
                    .username(USERNAME_USER1)
                    .completeName("John Doe");

            return this;
        }

        public Builder username(String username)
        {
            delegate.username(username);
            return this;
        }

        public Builder completeName(String completeName)
        {
            delegate.completeName(completeName);
            return this;
        }

        public UserSummary build()
        {
            return delegate.build();
        }
    }
}
