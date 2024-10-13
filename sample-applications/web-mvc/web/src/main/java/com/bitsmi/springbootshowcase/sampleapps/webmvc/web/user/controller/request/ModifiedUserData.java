package com.bitsmi.springbootshowcase.sampleapps.webmvc.web.user.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.Objects;
import java.util.Set;
import lombok.Builder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Builder(toBuilder = true, builderClassName = "Builder")
@Schema(description = "User data to be used in update operation")
public record ModifiedUserData(
        @NotNull
        @Schema(description = "New username of the user. Must be unique")
        String username,
        @NotNull
        @Schema(description = "New complete name of the user")
        String completeName,
        @Schema(description = "List of groups where the user will be enrolled replacing older ones")
        Set<@NotEmpty String> groups
) {
    @Override
    public String toString()
    {
        return new ToStringBuilder(this)
                .append("username", username)
                .append("completeName", completeName)
                .append("groups", groups)
                .build();
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(username);
    }

    @Override
    public boolean equals(Object o)
    {
        return this == o
                || o instanceof ModifiedUserData other
                && Objects.equals(username, other.username);
    }
}
