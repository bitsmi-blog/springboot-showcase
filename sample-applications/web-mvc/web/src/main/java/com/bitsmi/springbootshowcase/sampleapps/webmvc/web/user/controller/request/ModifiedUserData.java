package com.bitsmi.springbootshowcase.sampleapps.webmvc.web.user.controller.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.Objects;
import java.util.Set;
import lombok.Builder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Builder(toBuilder = true, builderClassName = "Builder")
public record ModifiedUserData(
    @NotNull String username,
    @NotNull String completeName,
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
