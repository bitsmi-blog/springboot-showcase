package com.bitsmi.springbootshowcase.sampleapps.webmvc.web.user.controller.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Objects;

@Builder(toBuilder = true, builderClassName = "Builder")
public record UpdateUserRequest(
        @NotNull @Valid ModifiedUserData data
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
                || o instanceof UpdateUserRequest other
                && Objects.equals(data, other.data);
    }
}
