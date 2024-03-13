package com.bitsmi.springbootshowcase.domain.common.impl;

import com.bitsmi.springbootshowcase.domain.common.IUserDomainCommandService;
import com.bitsmi.springbootshowcase.domain.common.UserConstants;
import com.bitsmi.springbootshowcase.domain.common.exception.ElementAlreadyExistsException;
import com.bitsmi.springbootshowcase.domain.common.model.User;
import com.bitsmi.springbootshowcase.domain.common.model.UserGroup;
import com.bitsmi.springbootshowcase.domain.common.spi.IUserGroupPersistenceService;
import com.bitsmi.springbootshowcase.domain.common.spi.IUserPersistenceService;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.nio.CharBuffer;
import java.util.Set;

public class UserDomainCommandServiceImpl implements IUserDomainCommandService
{
    private final IUserPersistenceService userPersistenceService;
    private final IUserGroupPersistenceService userGroupPersistenceService;
    private final PasswordEncoder passwordEncoder;

    public UserDomainCommandServiceImpl(IUserPersistenceService userPersistenceService,
                                        IUserGroupPersistenceService userGroupPersistenceService,
                                        PasswordEncoder passwordEncoder)
    {
        this.userPersistenceService = userPersistenceService;
        this.userGroupPersistenceService = userGroupPersistenceService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User createAdminUser(String username, char[] password)
    {
        boolean existUsers = userPersistenceService.countAllUsers()>0;
        if(existUsers) {
            throw new ElementAlreadyExistsException(User.class.getName(), "Users already created");
        }

        Set<UserGroup> groups = Set.of(
                userGroupPersistenceService.findUserGroupByName(UserConstants.USER_GROUP_USER).get(),
                userGroupPersistenceService.findUserGroupByName(UserConstants.USER_GROUP_ADMIN).get()
        );

        User user = User.builder()
                .username(username)
                .password(passwordEncoder.encode(CharBuffer.wrap(password)))
                .groups(groups)
                .active(Boolean.TRUE)
                .build();

        return userPersistenceService.createUser(user);
    }
}
