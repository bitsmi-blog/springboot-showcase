package com.bitsmi.springbootshowcase.sampleapps.application.common;

import com.bitsmi.springbootshowcase.sampleapps.domain.common.dto.PaginatedData;
import com.bitsmi.springbootshowcase.sampleapps.domain.common.dto.Pagination;
import com.bitsmi.springbootshowcase.sampleapps.domain.common.model.User;
import jakarta.validation.constraints.NotNull;

import java.util.Optional;

public interface UserRetrievalApplicationService
{
    PaginatedData<User> findAllUsers(Pagination page);

    Optional<User> findUserById(@NotNull Long id);
    Optional<User> findUserByUsername(@NotNull String username);
}
