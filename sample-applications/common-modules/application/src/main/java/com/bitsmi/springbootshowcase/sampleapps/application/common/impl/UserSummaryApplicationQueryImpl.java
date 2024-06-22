package com.bitsmi.springbootshowcase.sampleapps.application.common.impl;

import com.bitsmi.springbootshowcase.sampleapps.application.common.UserSummaryApplicationQuery;
import com.bitsmi.springbootshowcase.sampleapps.domain.common.UserDomainQueryService;
import com.bitsmi.springbootshowcase.sampleapps.domain.common.model.UserSummary;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

@Component
@Validated
public class UserSummaryApplicationQueryImpl implements UserSummaryApplicationQuery
{
    @Autowired
    private UserDomainQueryService userQueryDomainService;

    @Override
    public Optional<UserSummary> findUserSummaryByUsername(@NotNull String username)
    {
        return userQueryDomainService.findUserSummaryByUsername(username);
    }
}
