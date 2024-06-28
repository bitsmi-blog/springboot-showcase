package com.bitsmi.springbootshowcase.sampleapps.domain.common.impl;

import com.bitsmi.springbootshowcase.sampleapps.domain.common.UserConstants;
import com.bitsmi.springbootshowcase.sampleapps.domain.common.UserDomainFactory;
import com.bitsmi.springbootshowcase.sampleapps.domain.common.exception.ElementAlreadyExistsException;
import com.bitsmi.springbootshowcase.sampleapps.domain.common.model.User;
import com.bitsmi.springbootshowcase.sampleapps.domain.common.model.UserGroup;
import com.bitsmi.springbootshowcase.sampleapps.domain.common.UserGroupDomainRepository;
import com.bitsmi.springbootshowcase.sampleapps.domain.common.UserDomainRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.nio.CharBuffer;
import java.util.Set;

public class UserDomainFactoryImpl implements UserDomainFactory
{
    private final UserDomainRepository userRepositoryService;
    private final UserGroupDomainRepository userGroupRepositoryService;
    private final PasswordEncoder passwordEncoder;

    public UserDomainFactoryImpl(UserDomainRepository userRepositoryService,
                                 UserGroupDomainRepository userGroupRepositoryService,
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

        return User.builder()
                .username(username)
                .password(passwordEncoder.encode(CharBuffer.wrap(password)))
                .groups(groups)
                .active(Boolean.TRUE)
                .build();
    }
}
