package com.bitsmi.springbootshowcase.sampleapps.application.common;

import com.bitsmi.springbootshowcase.sampleapps.domain.common.model.UserSummary;
import jakarta.validation.constraints.NotNull;

import java.util.Optional;

public interface UserSummaryApplicationService
{
    Optional<UserSummary> findUserSummaryByUsername(@NotNull String username);
}
