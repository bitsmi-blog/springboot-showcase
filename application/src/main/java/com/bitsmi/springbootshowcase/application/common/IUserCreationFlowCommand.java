package com.bitsmi.springbootshowcase.application.common;

import com.bitsmi.springbootshowcase.domain.common.model.User;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

@Component
public interface IUserCreationFlowCommand
{
    User createAdminUser(@NotNull String username, @NotEmpty char[] password);
}
