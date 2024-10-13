package com.bitsmi.springbootshowcase.sampleapps.webmvc.web.user.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Objects;

@Builder(toBuilder = true, builderClassName = "Builder")
@Schema(description = "Request containing data needed to update an existing user")
public record UpdateUserRequest(
        @NotNull
        @Valid
        @Schema(description = "New data for the existing user")
        ModifiedUserData data
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
