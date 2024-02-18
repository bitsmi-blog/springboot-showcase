package com.bitsmi.springbootshowcase.domain.common.impl;

import com.bitsmi.springbootshowcase.domain.common.IUserCreationCommand;
import com.bitsmi.springbootshowcase.domain.common.UserConstants;
import com.bitsmi.springbootshowcase.domain.common.exception.ElementAlreadyExistsException;
import com.bitsmi.springbootshowcase.domain.common.model.User;
import com.bitsmi.springbootshowcase.domain.common.spi.IUserPersistenceService;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.nio.CharBuffer;
import java.util.List;

public class UserCreationCommandImpl implements IUserCreationCommand
{
    private final IUserPersistenceService userPersistenceService;
    private final PasswordEncoder passwordEncoder;

    public UserCreationCommandImpl(IUserPersistenceService userPersistenceService,
                               PasswordEncoder passwordEncoder)
    {
        this.userPersistenceService = userPersistenceService;
        this.passwordEncoder = passwordEncoder;
    }

    public User createAdminUser(String username, char[] password)
    {
        boolean existUsers = userPersistenceService.countAllUsers()>0;
        if(existUsers) {
            throw new ElementAlreadyExistsException(User.class.getName(), "Users already created");
        }

        String encodedPassword = passwordEncoder.encode(CharBuffer.wrap(password));
        return userPersistenceService.createUser(username, encodedPassword, List.of(UserConstants.USER_GROUP_ADMIN, UserConstants.USER_GROUP_USER));
    }
}
