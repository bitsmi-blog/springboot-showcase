package com.bitsmi.springbootshowcase.sampleapps.domain.common;

import com.bitsmi.springbootshowcase.sampleapps.domain.common.model.User;
import com.bitsmi.springbootshowcase.sampleapps.domain.common.model.UserSummary;

import java.util.Optional;

public interface IUserDomainQueryService
{
    Optional<User> findUserByUsername(String username);

    Optional<UserSummary> findUserSummaryByUsername(String username);
}
