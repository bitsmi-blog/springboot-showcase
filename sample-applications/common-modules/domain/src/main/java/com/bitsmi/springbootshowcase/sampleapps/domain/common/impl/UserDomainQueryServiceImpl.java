package com.bitsmi.springbootshowcase.sampleapps.domain.common.impl;

import com.bitsmi.springbootshowcase.sampleapps.domain.common.UserDomainQueryService;
import com.bitsmi.springbootshowcase.sampleapps.domain.common.model.User;
import com.bitsmi.springbootshowcase.sampleapps.domain.common.model.UserSummary;
import com.bitsmi.springbootshowcase.sampleapps.domain.common.UserRepositoryService;

import java.util.Optional;

public class UserDomainQueryServiceImpl implements UserDomainQueryService
{
    private final UserRepositoryService userRepositoryService;

    public UserDomainQueryServiceImpl(UserRepositoryService userRepositoryService)
    {
        this.userRepositoryService = userRepositoryService;
    }

    @Override
    public Optional<User> findUserByUsername(String username)
    {
        return userRepositoryService.findUserByUsername(username);
    }

    @Override
    public Optional<UserSummary> findUserSummaryByUsername(String username)
    {
        return userRepositoryService.findUserSummaryByUsername(username);
    }
}
