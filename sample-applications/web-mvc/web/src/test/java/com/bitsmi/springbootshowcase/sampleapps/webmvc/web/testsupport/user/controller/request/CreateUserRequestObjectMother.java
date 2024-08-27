package com.bitsmi.springbootshowcase.sampleapps.webmvc.web.testsupport.user.controller.request;

import com.bitsmi.springbootshowcase.sampleapps.domain.common.model.User;
import com.bitsmi.springbootshowcase.sampleapps.webmvc.web.user.controller.request.CreateUserRequest;
import com.bitsmi.springbootshowcase.sampleapps.webmvc.web.user.controller.request.NewUserData;

public final class CreateUserRequestObjectMother
{
    private CreateUserRequestObjectMother() { }

    public static CreateUserRequest fromModelWithPassword(User user, String password)
    {
        return builder()
            .data(NewUserDataObjectMother.fromModelWithPassword(user, password))
            .build();
    }

    public static Builder builder()
    {
        return new Builder();
    }

    public static final class Builder
    {
        private final CreateUserRequest.Builder delegate = CreateUserRequest.builder();

        public Builder data(NewUserData data)
        {
            delegate.data(data);
            return this;
        }

        public CreateUserRequest build()
        {
            return delegate.build();
        }
    }
}
