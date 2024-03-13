package com.bitsmi.springbootshowcase.application.common.impl;

import com.bitsmi.springbootshowcase.application.common.IUserSummaryApplicationQuery;
import com.bitsmi.springbootshowcase.domain.common.IUserDomainQueryService;
import com.bitsmi.springbootshowcase.domain.common.model.UserSummary;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

@Component
@Validated
public class UserSummaryApplicationQueryImpl implements IUserSummaryApplicationQuery
{
    @Autowired
    private IUserDomainQueryService userQueryDomainService;

    @Override
    public Optional<UserSummary> findUserSummaryByUsername(@NotNull String username)
    {
        return userQueryDomainService.findUserSummaryByUsername(username);
    }
}
