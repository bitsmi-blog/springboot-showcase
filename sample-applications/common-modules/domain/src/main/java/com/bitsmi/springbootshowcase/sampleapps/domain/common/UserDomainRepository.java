package com.bitsmi.springbootshowcase.sampleapps.domain.common;

import com.bitsmi.springbootshowcase.sampleapps.domain.common.dto.PaginatedData;
import com.bitsmi.springbootshowcase.sampleapps.domain.common.dto.Pagination;
import com.bitsmi.springbootshowcase.sampleapps.domain.common.model.User;
import jakarta.validation.constraints.NotNull;

import java.util.Optional;

public interface UserDomainRepository
{
    PaginatedData<User> findAllUsers(Pagination page);

    Long countAllUsers();

    Optional<User> findUserByUsername(@NotNull String username);

    Optional<User> findUserById(@NotNull Long id);

    User createUser(@NotNull User user);

    User updateUser(@NotNull Long id, @NotNull User user);

    void deleteUser(@NotNull Long id);
}
