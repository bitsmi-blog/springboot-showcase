package com.bitsmi.springbootshowcase.api.user.response;

import lombok.Builder;

import java.util.Objects;

@Builder(toBuilder = true, builderClassName = "Builder")
public record UserDetailsResponse(
        String username
)
{
    @Override
    public boolean equals(Object o)
    {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UserDetailsResponse other = (UserDetailsResponse) o;
        return Objects.equals(username, other.username);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(username);
    }
}
