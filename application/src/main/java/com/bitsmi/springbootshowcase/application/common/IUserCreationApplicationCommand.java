package com.bitsmi.springbootshowcase.application.common;

import com.bitsmi.springbootshowcase.domain.common.model.User;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public interface IUserCreationApplicationCommand
{
    User createAdminUser(@NotNull String username, @NotEmpty char[] password);
}
