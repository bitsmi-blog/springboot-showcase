package com.bitsmi.springbootshowcase.core.common;

import com.bitsmi.springbootshowcase.core.common.domain.User;
import jakarta.validation.constraints.NotNull;

import java.util.Optional;

public interface IUserManagementService
{
    boolean existUsers();

    Optional<User> findUserByUsername(@NotNull String username);

    User createUser(@NotNull String username, @NotNull String encodedPassword);
}
