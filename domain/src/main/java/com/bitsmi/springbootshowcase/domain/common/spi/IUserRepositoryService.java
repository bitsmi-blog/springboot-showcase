package com.bitsmi.springbootshowcase.domain.common.spi;

import com.bitsmi.springbootshowcase.domain.common.model.User;
import com.bitsmi.springbootshowcase.domain.common.model.UserSummary;
import jakarta.validation.constraints.NotNull;

import java.util.Optional;

public interface IUserRepositoryService
{
    Long countAllUsers();

    Optional<User> findUserByUsername(@NotNull String username);

    Optional<UserSummary> findUserSummaryByUsername(@NotNull String username);

    User createUser(@NotNull User user);
}
