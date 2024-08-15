package com.bitsmi.springbootshowcase.sampleapps.webmvc.web.testsupport.user.controller.request;

import com.bitsmi.springbootshowcase.sampleapps.domain.common.model.User;
import com.bitsmi.springbootshowcase.sampleapps.domain.common.model.UserGroup;
import com.bitsmi.springbootshowcase.sampleapps.webmvc.web.user.controller.request.CreateUserRequest;

import java.util.Set;
import java.util.stream.Collectors;

public final class CreateUserRequestObjectMother
{
    private CreateUserRequestObjectMother() { }

    public static CreateUserRequest fromModelWithPassword(User user, String password)
    {
        return builder()
                .fromModelWithPassword(user, password)
                .build();
    }

    public static Builder builder()
    {
        return new Builder();
    }

    public static final class Builder
    {
        private final CreateUserRequest.Builder delegate = CreateUserRequest.builder();

        public Builder fromModelWithPassword(User user, String password)
        {
            this.username(user.username())
                    .password(password)
                    .completeName(user.completeName())
                    .groups(user.groups()
                            .stream()
                            .map(UserGroup::name)
                            .collect(Collectors.toSet())
                    );

            return this;
        }

        public Builder username(String username)
        {
            delegate.username(username);
            return this;
        }

        public Builder password(String password)
        {
            delegate.password(password.toCharArray());
            return this;
        }

        public Builder completeName(String completeName)
        {
            delegate.completeName(completeName);
            return this;
        }

        public Builder groups(Set<String> groups)
        {
            delegate.groups(groups);
            return this;
        }

        public CreateUserRequest build()
        {
            return delegate.build();
        }
    }
}
