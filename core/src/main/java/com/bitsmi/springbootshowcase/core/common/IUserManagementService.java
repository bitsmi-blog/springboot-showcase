package com.bitsmi.springbootshowcase.core.common;

import com.bitsmi.springbootshowcase.core.common.domain.User;
import jakarta.validation.constraints.NotNull;

public interface IUserManagementService
{
    boolean existUsers();

    User createUser(@NotNull String username, @NotNull String encodedPassword);
}
