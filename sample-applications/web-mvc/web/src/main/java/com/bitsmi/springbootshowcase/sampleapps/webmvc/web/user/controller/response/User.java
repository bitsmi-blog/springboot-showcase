package com.bitsmi.springbootshowcase.sampleapps.webmvc.web.user.controller.response;

import lombok.Builder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

@Builder(toBuilder = true, builderClassName = "Builder")
public record User(
        Long id,
        String username,
        String completeName,
        Boolean active,
        Set<String> groups,
        LocalDateTime creationDate,
        LocalDateTime lastUpdated
)
{
    public User
    {
        groups = Set.copyOf(groups);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("username", username)
                .append("completeName", completeName)
                .append("active", active)
                .append("groups", groups)
                .append("creationDate", creationDate)
                .append("lastUpdated", lastUpdated)
                .build();
    }

    @Override
    public boolean equals(Object o)
    {
        return this == o
                || o instanceof User other
                && Objects.equals(username, other.username);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(username);
    }
}
