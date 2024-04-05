package com.bitsmi.springbootshowcase.domain.common.impl;

import com.bitsmi.springbootshowcase.domain.common.IUserDomainCommandService;
import com.bitsmi.springbootshowcase.domain.common.UserConstants;
import com.bitsmi.springbootshowcase.domain.common.exception.ElementAlreadyExistsException;
import com.bitsmi.springbootshowcase.domain.common.model.User;
import com.bitsmi.springbootshowcase.domain.common.model.UserGroup;
import com.bitsmi.springbootshowcase.domain.common.spi.IUserGroupRepositoryService;
import com.bitsmi.springbootshowcase.domain.common.spi.IUserRepositoryService;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.nio.CharBuffer;
import java.util.Set;

public class UserDomainCommandServiceImpl implements IUserDomainCommandService
{
    private final IUserRepositoryService userRepositoryService;
    private final IUserGroupRepositoryService userGroupRepositoryService;
    private final PasswordEncoder passwordEncoder;

    public UserDomainCommandServiceImpl(IUserRepositoryService userRepositoryService,
                                        IUserGroupRepositoryService userGroupRepositoryService,
                                        PasswordEncoder passwordEncoder)
    {
        this.userRepositoryService = userRepositoryService;
        this.userGroupRepositoryService = userGroupRepositoryService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User createAdminUser(String username, char[] password)
    {
        boolean existUsers = userRepositoryService.countAllUsers()>0;
        if(existUsers) {
            throw new ElementAlreadyExistsException(User.class.getName(), "Users already created");
        }

        Set<UserGroup> groups = Set.of(
                userGroupRepositoryService.findUserGroupByName(UserConstants.USER_GROUP_USER).get(),
                userGroupRepositoryService.findUserGroupByName(UserConstants.USER_GROUP_ADMIN).get()
        );

        User user = User.builder()
                .username(username)
                .password(passwordEncoder.encode(CharBuffer.wrap(password)))
                .groups(groups)
                .active(Boolean.TRUE)
                .build();

        return userRepositoryService.createUser(user);
    }
}
