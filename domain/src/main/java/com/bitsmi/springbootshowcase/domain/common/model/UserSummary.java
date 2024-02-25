package com.bitsmi.springbootshowcase.domain.common.model;

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
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UserSummary other = (UserSummary) o;
        return Objects.equals(username, other.username);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(username);
    }
}
