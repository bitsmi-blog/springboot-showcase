package com.bitsmi.springbootshowcase.sampleapps.domain.common.model;

import lombok.Builder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Objects;

@Builder(toBuilder = true, builderClassName = "Builder")
public record UserSummary(
        String username,
        String completeName
)
{
    @Override
    public String toString()
    {
        return new ToStringBuilder(this)
                .append("username", username)
                .append("completeName", completeName)
                .build();
    }

    @Override
    public boolean equals(Object o)
    {
        return this == o
                || o instanceof UserSummary other
                    && Objects.equals(username, other.username);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(username);
    }
}
