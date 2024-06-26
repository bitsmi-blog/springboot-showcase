package com.bitsmi.springbootshowcase.sampleapps.application.common;

import com.bitsmi.springbootshowcase.sampleapps.domain.common.model.User;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public interface UserCreationApplicationService
{
    User createAdminUser(@NotNull String username, @NotEmpty char[] password);
}
