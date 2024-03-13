package com.bitsmi.springbootshowcase.application.common;

import com.bitsmi.springbootshowcase.domain.common.model.UserSummary;
import jakarta.validation.constraints.NotNull;

import java.util.Optional;

public interface IUserSummaryApplicationQuery
{
    Optional<UserSummary> findUserSummaryByUsername(@NotNull String username);
}
