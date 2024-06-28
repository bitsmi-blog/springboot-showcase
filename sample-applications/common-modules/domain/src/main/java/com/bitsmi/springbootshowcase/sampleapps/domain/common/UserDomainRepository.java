package com.bitsmi.springbootshowcase.sampleapps.domain.common;

import com.bitsmi.springbootshowcase.sampleapps.domain.common.model.User;
import com.bitsmi.springbootshowcase.sampleapps.domain.common.model.UserSummary;
import jakarta.validation.constraints.NotNull;

import java.util.Optional;

public interface UserDomainRepository
{
    Long countAllUsers();

    Optional<User> findUserByUsername(@NotNull String username);

    Optional<UserSummary> findUserSummaryByUsername(@NotNull String username);

    User createUser(@NotNull User user);
}
