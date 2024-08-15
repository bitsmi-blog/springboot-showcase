package com.bitsmi.springbootshowcase.sampleapps.application.common.impl;

import com.bitsmi.springbootshowcase.sampleapps.application.common.UserRegistryApplicationService;
import com.bitsmi.springbootshowcase.sampleapps.domain.common.UserDomainFactory;
import com.bitsmi.springbootshowcase.sampleapps.domain.common.UserDomainRepository;
import com.bitsmi.springbootshowcase.sampleapps.domain.common.exception.ElementAlreadyExistsException;
import com.bitsmi.springbootshowcase.sampleapps.domain.common.exception.ElementNotFoundException;
import com.bitsmi.springbootshowcase.sampleapps.domain.common.model.User;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.Set;

@Component
@Validated
public class UserRegistryApplicationServiceImpl implements UserRegistryApplicationService
{
    private final UserDomainFactory userDomainFactory;
    private final UserDomainRepository userDomainRepository;

    public UserRegistryApplicationServiceImpl(
            UserDomainFactory userDomainFactory,
            UserDomainRepository userDomainRepository
    ) {
        this.userDomainFactory = userDomainFactory;
        this.userDomainRepository = userDomainRepository;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public User createUser(
            @NotNull String username,
            @NotEmpty char[] password,
            @NotNull String completeName,
            @NotNull Set<String> groupNames
    ) {
        if(userDomainRepository.findUserByUsername(username).isPresent()) {
            throw new ElementAlreadyExistsException(
                    User.class.getName(),
                    "User with username (%s) already exists".formatted(username)
            );
        }

        User user = userDomainFactory.createUser(
                username,
                password,
                completeName,
                groupNames
        );
        return userDomainRepository.createUser(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public User createAdminUser(@NotNull String username, @NotEmpty char[] password)
    {
        boolean existUsers = userDomainRepository.countAllUsers()>0;
        if(existUsers) {
            throw new ElementAlreadyExistsException(User.class.getName(), "Users already created");
        }

        User user = userDomainFactory.createAdminUser(username, password);
        return userDomainRepository.createUser(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public User updateUser(
            @NotNull Long id,
            @NotNull String username,
            @NotNull String completeName,
            @NotNull Set<String> groupNames
    ) {
        User existingUser = userDomainRepository.findUserById(id)
                .orElseThrow(() -> new ElementNotFoundException(
                                User.class.getName(),
                                "User with id (%s) not found".formatted(id)
                        )
                );

        User updatedUser = userDomainFactory.updatedUser(
                existingUser,
                username,
                completeName,
                groupNames
        );
        return userDomainRepository.updateUser(id, updatedUser);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void changeUserPassword(@NotNull Long id, @NotEmpty char[] password)
    {
        User existingUser = userDomainRepository.findUserById(id)
                .orElseThrow(() -> new ElementNotFoundException(
                                User.class.getName(),
                                "User with id (%s) not found".formatted(id)
                        )
                );

        User updatedUser = existingUser.toBuilder()
                .password(userDomainFactory.encodePassword(password))
                .build();
        userDomainRepository.updateUser(id, updatedUser);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteUser(@NotNull Long id)
    {
        userDomainRepository.deleteUser(id);
    }
}
