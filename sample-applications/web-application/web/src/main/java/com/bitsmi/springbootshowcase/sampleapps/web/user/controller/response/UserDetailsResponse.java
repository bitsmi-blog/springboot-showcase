package com.bitsmi.springbootshowcase.sampleapps.web.user.controller.response;

import lombok.Builder;

import java.util.Objects;

@Builder(toBuilder = true, builderClassName = "Builder")
public record UserDetailsResponse(
        String username,
        String completeName
)
{
    @Override
    public boolean equals(Object o)
    {
        return this == o
                || o instanceof UserDetailsResponse other
                    && Objects.equals(username, other.username);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(username);
    }
}
