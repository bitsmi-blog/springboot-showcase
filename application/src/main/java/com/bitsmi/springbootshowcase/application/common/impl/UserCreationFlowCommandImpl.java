package com.bitsmi.springbootshowcase.application.common.impl;

import com.bitsmi.springbootshowcase.application.common.IUserCreationFlowCommand;
import com.bitsmi.springbootshowcase.domain.common.IUserCreationCommand;
import com.bitsmi.springbootshowcase.domain.common.model.User;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@Validated
public class UserCreationFlowCommandImpl implements IUserCreationFlowCommand
{
    @Autowired
    private IUserCreationCommand userCreationCommand;

    public User createAdminUser(@NotNull String username, @NotEmpty char[] password)
    {
        return userCreationCommand.createAdminUser(username, password);
    }
}
