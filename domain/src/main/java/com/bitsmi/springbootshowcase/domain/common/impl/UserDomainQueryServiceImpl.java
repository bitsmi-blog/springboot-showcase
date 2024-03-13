package com.bitsmi.springbootshowcase.domain.common.impl;

import com.bitsmi.springbootshowcase.domain.common.IUserDomainQueryService;
import com.bitsmi.springbootshowcase.domain.common.model.User;
import com.bitsmi.springbootshowcase.domain.common.model.UserSummary;
import com.bitsmi.springbootshowcase.domain.common.spi.IUserPersistenceService;

import java.util.Optional;

public class UserDomainQueryServiceImpl implements IUserDomainQueryService
{
    private final IUserPersistenceService userPersistenceService;

    public UserDomainQueryServiceImpl(IUserPersistenceService userPersistenceService)
    {
        this.userPersistenceService = userPersistenceService;
    }

    @Override
    public Optional<User> findUserByUsername(String username)
    {
        return userPersistenceService.findUserByUsername(username);
    }

    @Override
    public Optional<UserSummary> findUserSummaryByUsername(String username)
    {
        return userPersistenceService.findUserSummaryByUsername(username);
    }
}
