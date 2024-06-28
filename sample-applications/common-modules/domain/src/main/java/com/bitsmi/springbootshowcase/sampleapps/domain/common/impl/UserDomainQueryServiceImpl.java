package com.bitsmi.springbootshowcase.sampleapps.domain.common.impl;

import com.bitsmi.springbootshowcase.sampleapps.domain.common.UserDomainQueryService;
import com.bitsmi.springbootshowcase.sampleapps.domain.common.model.User;
import com.bitsmi.springbootshowcase.sampleapps.domain.common.model.UserSummary;
import com.bitsmi.springbootshowcase.sampleapps.domain.common.UserDomainRepository;

import java.util.Optional;

public class UserDomainQueryServiceImpl implements UserDomainQueryService
{
    private final UserDomainRepository userRepositoryService;

    public UserDomainQueryServiceImpl(UserDomainRepository userRepositoryService)
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
