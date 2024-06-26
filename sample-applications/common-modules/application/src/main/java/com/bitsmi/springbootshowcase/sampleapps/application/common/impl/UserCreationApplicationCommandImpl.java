package com.bitsmi.springbootshowcase.sampleapps.application.common.impl;

import com.bitsmi.springbootshowcase.sampleapps.application.common.UserCreationApplicationCommand;
import com.bitsmi.springbootshowcase.sampleapps.domain.common.UserDomainFactory;
import com.bitsmi.springbootshowcase.sampleapps.domain.common.exception.ElementAlreadyExistsException;
import com.bitsmi.springbootshowcase.sampleapps.domain.common.model.User;
import com.bitsmi.springbootshowcase.sampleapps.domain.common.UserRepositoryService;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Component
@Validated
public class UserCreationApplicationCommandImpl implements UserCreationApplicationCommand
{
    private final UserDomainFactory userDomainFactory;
    private final UserRepositoryService userRepositoryService;

    public UserCreationApplicationCommandImpl(
            UserDomainFactory userDomainFactory,
            UserRepositoryService userRepositoryService
    ) {
        this.userDomainFactory = userDomainFactory;
        this.userRepositoryService = userRepositoryService;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public User createAdminUser(@NotNull String username, @NotEmpty char[] password)
    {
        boolean existUsers = userRepositoryService.countAllUsers()>0;
        if(existUsers) {
            throw new ElementAlreadyExistsException(User.class.getName(), "Users already created");
        }

        User user = userDomainFactory.createAdminUser(username, password);
        return userRepositoryService.createUser(user);
    }
}
