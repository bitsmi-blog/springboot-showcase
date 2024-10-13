package com.bitsmi.springbootshowcase.sampleapps.webmvc.web.user.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.Objects;
import lombok.Builder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Builder(toBuilder = true, builderClassName = "Builder")
@Schema(description = "Request containing data needed to create a new user")
public record CreateUserRequest(
        @NotNull
        @Valid
        @Schema(description = "Data of the new user")
        NewUserData data
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
