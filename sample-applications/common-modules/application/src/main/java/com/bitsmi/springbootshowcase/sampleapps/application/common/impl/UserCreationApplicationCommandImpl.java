package com.bitsmi.springbootshowcase.sampleapps.application.common.impl;

import com.bitsmi.springbootshowcase.sampleapps.application.common.IUserCreationApplicationCommand;
import com.bitsmi.springbootshowcase.sampleapps.domain.common.IUserDomainCommandService;
import com.bitsmi.springbootshowcase.sampleapps.domain.common.model.User;
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
    private IUserDomainCommandService userCommandDomainService;

    @Override
    public User createAdminUser(@NotNull String username, @NotEmpty char[] password)
    {
        return userCommandDomainService.createAdminUser(username, password);
    }
}
