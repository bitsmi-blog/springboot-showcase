package com.bitsmi.springbootshowcase.sampleapps.domain.common.impl;

import com.bitsmi.springbootshowcase.sampleapps.domain.common.UserConstants;
import com.bitsmi.springbootshowcase.sampleapps.domain.common.UserDomainFactory;
import com.bitsmi.springbootshowcase.sampleapps.domain.common.UserGroupDomainRepository;
import com.bitsmi.springbootshowcase.sampleapps.domain.common.exception.ElementNotFoundException;
import com.bitsmi.springbootshowcase.sampleapps.domain.common.model.User;
import com.bitsmi.springbootshowcase.sampleapps.domain.common.model.UserGroup;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserDomainFactoryImpl implements UserDomainFactory
{
    private final UserGroupDomainRepository userGroupDomainRepository;
    private final PasswordEncoder passwordEncoder;

    public UserDomainFactoryImpl(UserGroupDomainRepository userGroupDomainRepository,
                                 PasswordEncoder passwordEncoder)
    {
        this.userGroupDomainRepository = userGroupDomainRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User createUser(String username, char[] password, String completeName, Set<String> groupNames)
    {
        Set<UserGroup> groups = new HashSet<>();
        List<String> missingGroups = new ArrayList<>();
        groupNames.forEach(groupName -> {
            userGroupDomainRepository.findUserGroupByName(groupName)
                    .ifPresentOrElse(
                            groups::add,
                            () -> missingGroups.add(groupName)
                    );
        });

        if(!missingGroups.isEmpty()) {
            throw new ElementNotFoundException(
                    UserGroup.class.getName(),
                    "The following user groups doesn't exists: %s".formatted(String.join(", ", missingGroups))
            );
        }

        // Ensure USER group
        if(!groupNames.contains(UserConstants.USER_GROUP_USER)) {
            groups.add(userGroupDomainRepository.findUserGroupByName(UserConstants.USER_GROUP_USER).get());
        }

        return User.builder()
                .username(username)
                .password(passwordEncoder.encode(CharBuffer.wrap(password)))
                .completeName(completeName)
                .groups(groups)
                .active(Boolean.TRUE)
                .build();
    }

    @Override
    public User createAdminUser(String username, char[] password)
    {
        Set<UserGroup> groups = Set.of(
                userGroupDomainRepository.findUserGroupByName(UserConstants.USER_GROUP_USER).get(),
                userGroupDomainRepository.findUserGroupByName(UserConstants.USER_GROUP_ADMIN).get()
        );

        return User.builder()
                .username(username)
                .password(encodePassword(password))
                .completeName("Admin")
                .groups(groups)
                .active(Boolean.TRUE)
                .build();
    }

    @Override
    public User updatedUser(User userToUpdate, String username, String completeName, Set<String> groupNames)
    {
        Set<UserGroup> groups = new HashSet<>();
        List<String> missingGroups = new ArrayList<>();
        groupNames.forEach(groupName -> {
            userGroupDomainRepository.findUserGroupByName(groupName)
                    .ifPresentOrElse(
                            groups::add,
                            () -> missingGroups.add(groupName)
                    );
        });

        if(!missingGroups.isEmpty()) {
            throw new ElementNotFoundException(
                    UserGroup.class.getName(),
                    "The following user groups doesn't exists: %s".formatted(String.join(", ", missingGroups))
            );
        }

        // Ensure USER group
        if(!groupNames.contains(UserConstants.USER_GROUP_USER)) {
            groups.add(userGroupDomainRepository.findUserGroupByName(UserConstants.USER_GROUP_USER).get());
        }

        return userToUpdate.toBuilder()
                .username(username)
                .completeName(completeName)
                .groups(groups)
                .build();
    }

    @Override
    public String encodePassword(char[] password)
    {
        return passwordEncoder.encode(CharBuffer.wrap(password));
    }
}
