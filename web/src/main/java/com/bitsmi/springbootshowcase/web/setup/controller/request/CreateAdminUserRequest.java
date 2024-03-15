package com.bitsmi.springbootshowcase.web.setup.controller.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.Arrays;
import java.util.Objects;

@Builder(toBuilder = true, builderClassName = "Builder")
public record CreateAdminUserRequest(
        @NotNull
        String username,
        @NotNull
        @NotEmpty
        char[] password
)
{
    public void clearPassword()
    {
        Arrays.fill(password, '0');
    }

    @Override
    public boolean equals(Object o)
    {
        return this == o
                || o instanceof CreateAdminUserRequest other
                    && Objects.equals(username, other.username);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(username);
    }
}
