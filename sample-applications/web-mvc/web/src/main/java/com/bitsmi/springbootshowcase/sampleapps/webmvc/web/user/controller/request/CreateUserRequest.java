package com.bitsmi.springbootshowcase.sampleapps.webmvc.web.user.controller.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.Objects;
import lombok.Builder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Builder(toBuilder = true, builderClassName = "Builder")
public record CreateUserRequest(
        @NotNull @Valid NewUserData data
) {
    @Override
    public String toString()
    {
        return new ToStringBuilder(this)
                .append("data", data)
                .build();
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(data);
    }

    @Override
    public boolean equals(Object o)
    {
        return this == o
                || o instanceof CreateUserRequest other
                && Objects.equals(data, other.data);
    }
}
