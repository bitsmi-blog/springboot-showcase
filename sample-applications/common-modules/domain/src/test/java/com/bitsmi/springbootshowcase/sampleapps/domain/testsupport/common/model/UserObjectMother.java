package com.bitsmi.springbootshowcase.sampleapps.domain.testsupport.common.model;

import com.bitsmi.springbootshowcase.sampleapps.domain.common.model.User;

import java.time.LocalDateTime;
import java.util.Collections;

public final class UserObjectMother
{
    public static final Long ID_USER1 = 1001L;
    public static final String USERNAME_USER1 = "john.doe";

    private UserObjectMother() { }

    public static User user1()
    {
        return builder()
                .user1()
                .build();
    }

    public static UserObjectMother.Builder builder()
    {
        return new UserObjectMother.Builder();
    }

    public static final class Builder
    {
        private final User.Builder delegate = User.builder();

        public Builder user1()
        {
            delegate
                    .id(ID_USER1)
                    .username(USERNAME_USER1)
                    .password("password.john.doe")
                    .completeName("John Doe")
                    .active(Boolean.TRUE)
                    .groups(Collections.emptySet())
                    .creationDate(LocalDateTime.now())
                    .lastUpdated(LocalDateTime.now());

            return this;
        }

        public Builder id(Long id)
        {
            delegate.id(id);
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

        public User build()
        {
            return delegate.build();
        }
    }
}
