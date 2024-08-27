package com.bitsmi.springbootshowcase.sampleapps.webmvc.web.testsupport.user.controller.request;

import com.bitsmi.springbootshowcase.sampleapps.domain.common.model.User;
import com.bitsmi.springbootshowcase.sampleapps.domain.common.model.UserGroup;
import com.bitsmi.springbootshowcase.sampleapps.webmvc.web.user.controller.request.ModifyUserData;
import java.util.Set;
import java.util.stream.Collectors;

public final class ModifyUserDataObjectMother
{
    private ModifyUserDataObjectMother() { }

    public static ModifyUserData fromModel(User user)
    {
        return builder()
            .fromModel(user)
            .build();
    }

    public static Builder builder()
    {
        return new Builder();
    }

    public static final class Builder
    {
        private final ModifyUserData.Builder delegate = ModifyUserData.builder();

        public Builder fromModel(User user)
        {
            this.username(user.username())
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

        public ModifyUserData build()
        {
            return delegate.build();
        }
    }
}
