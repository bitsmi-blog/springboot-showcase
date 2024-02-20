package com.bitsmi.springbootshowcase.application.common.impl;

import com.bitsmi.springbootshowcase.application.common.IUserCreationApplicationCommand;
import com.bitsmi.springbootshowcase.domain.common.IUserCommandDomainService;
import com.bitsmi.springbootshowcase.domain.common.model.User;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@Validated
public class UserCreationApplicationCommandImpl implements IUserCreationApplicationCommand
{
    @Autowired
    private IUserCommandDomainService userCommandDomainService;

    public User createAdminUser(@NotNull String username, @NotEmpty char[] password)
    {
        return userCommandDomainService.createAdminUser(username, password);
    }
}
