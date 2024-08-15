package com.bitsmi.springbootshowcase.sampleapps.domain.testsupport.common.model;

import com.bitsmi.springbootshowcase.sampleapps.domain.common.UserConstants;
import com.bitsmi.springbootshowcase.sampleapps.domain.common.model.UserGroup;

import java.time.LocalDateTime;
import java.util.Collections;

public class UserGroupObjectMother
{
    private static final LocalDateTime A_DATE = LocalDateTime.of(2024, 1, 1, 0, 0);

    public static final UserGroup ADMIN = admin();
    public static final UserGroup USER = user();

    private UserGroupObjectMother() { }

    public static UserGroup admin()
    {
        return builder()
                .admin()
                .build();
    }

    public static UserGroup user()
    {
        return builder()
                .user()
                .build();
    }

    public static Builder builder()
    {
        return new Builder();
    }

    public static final class Builder
    {
        private final UserGroup.Builder delegate = UserGroup.builder();

        public Builder admin()
        {
            delegate
                    .id(1001L)
                    .name(UserConstants.USER_GROUP_ADMIN)
                    .description("Admin Group")
                    .authorities(Collections.emptySet())
                    .creationDate(A_DATE)
                    .lastUpdated(A_DATE);

            return this;
        }

        public Builder user()
        {
            delegate
                    .id(1002L)
                    .name(UserConstants.USER_GROUP_USER)
                    .description("User Group")
                    .authorities(Collections.emptySet())
                    .creationDate(A_DATE)
                    .lastUpdated(A_DATE);

            return this;
        }

        public Builder id(Long id)
        {
            delegate.id(id);
            return this;
        }

        public Builder name(String name)
        {
            delegate.name(name);
            return this;
        }

        public Builder description(String description)
        {
            delegate.description(description);
            return this;
        }

        public UserGroup build()
        {
            return delegate.build();
        }
    }
}
