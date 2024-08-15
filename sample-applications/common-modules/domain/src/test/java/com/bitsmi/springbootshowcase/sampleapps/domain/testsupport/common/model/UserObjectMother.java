package com.bitsmi.springbootshowcase.sampleapps.domain.testsupport.common.model;

import com.bitsmi.springbootshowcase.sampleapps.domain.common.model.User;
import com.bitsmi.springbootshowcase.sampleapps.domain.common.model.UserGroup;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public final class UserObjectMother
{
    private static final LocalDateTime A_DATE = LocalDateTime.of(2024, 1, 1, 0, 0);

    public static User AN_ADMIN_USER = anAdminUser();
    public static User A_NORMAL_USER = aNormalUser();
    public static User A_NON_EXISTING_USER = aNonExistingUser();

    private UserObjectMother() { }

    public static User anAdminUser()
    {
        return builder()
                .anAdminUser()
                .build();
    }

    public static User aNormalUser()
    {
        return builder()
                .aNormalUser()
                .build();
    }

    public static User aNonExistingUser()
    {
        return builder()
                .aNonExistingUser()
                .build();
    }

    public static Builder builder()
    {
        return new UserObjectMother.Builder();
    }

    public static final class Builder
    {
        private final User.Builder delegate = User.builder();

        public Builder anAdminUser()
        {
            return this.id(1001L)
                    .username("john.admin")
                    .password("password.john.admin")
                    .completeName("John Admin")
                    .active(Boolean.TRUE)
                    .groups(Set.of(UserGroupObjectMother.ADMIN, UserGroupObjectMother.USER))
                    .creationDate(A_DATE)
                    .lastUpdated(A_DATE);
        }

        public Builder aNormalUser()
        {
            return this.id(1002L)
                    .username("jane.normal")
                    .password("password.jane.normal")
                    .completeName("Jane Normal")
                    .active(Boolean.TRUE)
                    .groups(Set.of(UserGroupObjectMother.USER))
                    .creationDate(A_DATE)
                    .lastUpdated(A_DATE);
        }

        public Builder aNonExistingUser()
        {
            return this.id(9999L)
                    .username("peter.non-existing")
                    .password("password.peter.non-existing")
                    .completeName("Peter NonExisting")
                    .active(Boolean.TRUE)
                    .groups(Set.of(UserGroupObjectMother.USER))
                    .creationDate(A_DATE)
                    .lastUpdated(A_DATE);
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

        public Builder password(String password)
        {
            delegate.password(password);
            return this;
        }

        public Builder completeName(String completeName)
        {
            delegate.completeName(completeName);
            return this;
        }

        public Builder active(Boolean active)
        {
            delegate.active(active);
            return this;
        }

        public Builder groups(UserGroup... groups)
        {
            delegate.groups(Set.of(groups));
            return this;
        }

        public Builder groups(Collection<UserGroup> groups)
        {
            delegate.groups(new HashSet<>(groups));
            return this;
        }

        public Builder creationDate(LocalDateTime creationDate)
        {
            delegate.creationDate(creationDate);
            return this;
        }

        public Builder lastUpdated(LocalDateTime lastUpdated)
        {
            delegate.lastUpdated(lastUpdated);
            return this;
        }

        public User build()
        {
            return delegate.build();
        }
    }
}
