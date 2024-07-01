package com.bitsmi.springbootshowcase.sampleapps.domain.common.impl;

import com.bitsmi.springbootshowcase.sampleapps.domain.common.UserQueriesDomainService;
import com.bitsmi.springbootshowcase.sampleapps.domain.common.model.User;
import com.bitsmi.springbootshowcase.sampleapps.domain.common.model.UserSummary;
import com.bitsmi.springbootshowcase.sampleapps.domain.common.UserDomainRepository;

import java.util.Optional;

public class UserQueriesDomainServiceImpl implements UserQueriesDomainService
{
    private final UserDomainRepository userRepositoryService;

    public UserQueriesDomainServiceImpl(UserDomainRepository userRepositoryService)
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
