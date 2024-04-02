package com.bitsmi.springbootshowcase.domain.common.impl;

import com.bitsmi.springbootshowcase.domain.common.IUserDomainQueryService;
import com.bitsmi.springbootshowcase.domain.common.model.User;
import com.bitsmi.springbootshowcase.domain.common.model.UserSummary;
import com.bitsmi.springbootshowcase.domain.common.spi.IUserRepositoryService;

import java.util.Optional;

public class UserDomainQueryServiceImpl implements IUserDomainQueryService
{
    private final IUserRepositoryService userRepositoryService;

    public UserDomainQueryServiceImpl(IUserRepositoryService userRepositoryService)
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
