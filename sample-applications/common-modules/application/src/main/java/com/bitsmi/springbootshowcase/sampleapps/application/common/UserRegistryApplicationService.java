package com.bitsmi.springbootshowcase.sampleapps.application.common;

import com.bitsmi.springbootshowcase.sampleapps.domain.common.model.User;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public interface UserRegistryApplicationService
{
    User createUser(
            @NotNull String username,
            @NotEmpty char[] password,
            @NotNull String completeName,
            @NotNull Set<String> groupNames
    );

    User createAdminUser(@NotNull String username, @NotEmpty char[] password);

    User updateUser(
            @NotNull Long id,
            @NotNull String username,
            @NotNull String completeName,
            @NotNull Set<String> groupNames
    );

    void changeUserPassword(@NotNull Long id, @NotEmpty char[] password);

    void deleteUser(@NotNull Long id);
}
