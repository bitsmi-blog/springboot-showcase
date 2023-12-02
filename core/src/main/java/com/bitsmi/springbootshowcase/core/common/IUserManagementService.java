package com.bitsmi.springbootshowcase.core.common;

import com.bitsmi.springbootshowcase.core.common.model.User;
import com.bitsmi.springbootshowcase.core.common.model.UserSummary;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.Optional;

public interface IUserManagementService
{
    boolean existUsers();

    Optional<User> findUserByUsername(@NotNull String username);

    Optional<UserSummary> findUserSummaryByUsername(@NotNull String username);

    User createUser(@NotNull String username, @NotNull String encodedPassword, @NotNull List<String> groups);
}
