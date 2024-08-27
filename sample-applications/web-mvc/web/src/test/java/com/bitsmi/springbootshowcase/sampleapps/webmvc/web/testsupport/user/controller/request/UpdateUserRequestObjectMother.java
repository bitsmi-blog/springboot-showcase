package com.bitsmi.springbootshowcase.sampleapps.webmvc.web.testsupport.user.controller.request;

import com.bitsmi.springbootshowcase.sampleapps.domain.common.model.User;
import com.bitsmi.springbootshowcase.sampleapps.webmvc.web.user.controller.request.ModifyUserData;
import com.bitsmi.springbootshowcase.sampleapps.webmvc.web.user.controller.request.UpdateUserRequest;

public final class UpdateUserRequestObjectMother
{
    private UpdateUserRequestObjectMother() { }

    public static UpdateUserRequest fromModel(User user)
    {
        return builder()
                .data(ModifyUserDataObjectMother.fromModel(user))
                .build();
    }

    public static Builder builder()
    {
        return new Builder();
    }

    public static final class Builder
    {
        private final UpdateUserRequest.Builder delegate = UpdateUserRequest.builder();

        public Builder data(ModifyUserData data)
        {
            delegate.data(data);
            return this;
        }

        public UpdateUserRequest build()
        {
            return delegate.build();
        }
    }
}
