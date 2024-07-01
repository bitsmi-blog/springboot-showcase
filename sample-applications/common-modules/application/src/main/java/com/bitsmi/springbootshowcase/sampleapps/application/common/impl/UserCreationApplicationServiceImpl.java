package com.bitsmi.springbootshowcase.sampleapps.application.common.impl;

import com.bitsmi.springbootshowcase.sampleapps.application.common.UserCreationApplicationService;
import com.bitsmi.springbootshowcase.sampleapps.domain.common.UserDomainFactory;
import com.bitsmi.springbootshowcase.sampleapps.domain.common.exception.ElementAlreadyExistsException;
import com.bitsmi.springbootshowcase.sampleapps.domain.common.model.User;
import com.bitsmi.springbootshowcase.sampleapps.domain.common.UserDomainRepository;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Component
@Validated
public class UserCreationApplicationServiceImpl implements UserCreationApplicationService
{
    private final UserDomainFactory userDomainFactory;
    private final UserDomainRepository userRepositoryService;

    public UserCreationApplicationServiceImpl(
            UserDomainFactory userDomainFactory,
            UserDomainRepository userRepositoryService
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
