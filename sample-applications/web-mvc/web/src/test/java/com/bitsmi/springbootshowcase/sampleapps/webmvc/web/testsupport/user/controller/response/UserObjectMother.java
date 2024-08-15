package com.bitsmi.springbootshowcase.sampleapps.webmvc.web.testsupport.user.controller.response;

import com.bitsmi.springbootshowcase.sampleapps.domain.common.model.UserGroup;
import com.bitsmi.springbootshowcase.sampleapps.webmvc.web.user.controller.response.User;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public final class UserObjectMother
{
    private UserObjectMother() { }

    public static User fromModel(com.bitsmi.springbootshowcase.sampleapps.domain.common.model.User user)
    {
        return builder()
                .id(user.id())
                .username(user.username())
                .completeName(user.completeName())
                .active(user.active())
                .groups(user.groups()
                        .stream()
                        .map(UserGroup::name)
                        .collect(Collectors.toSet())
                )
                .creationDate(user.creationDate())
                .lastUpdated(user.lastUpdated())
                .build();
    }

    public static Builder builder()
    {
        return new Builder();
    }

    public static final class Builder
    {
        private final User.Builder delegate = User.builder();

        public Builder fromModel(com.bitsmi.springbootshowcase.sampleapps.domain.common.model.User user)
        {
            return this.id(user.id())
                    .username(user.username())
                    .completeName(user.completeName())
                    .active(user.active())
                    .groups(user.groups()
                            .stream()
                            .map(UserGroup::name)
                            .collect(Collectors.toSet())
                    )
                    .creationDate(user.creationDate())
                    .lastUpdated(user.lastUpdated());
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

        public Builder active(Boolean active)
        {
            delegate.active(active);
            return this;
        }

        public Builder groups(String... groups)
        {
            delegate.groups(Set.of(groups));
            return this;
        }

        public Builder groups(Collection<String> groups)
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
