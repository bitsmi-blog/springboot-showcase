package com.bitsmi.springbootshowcase.core.common.model;

import lombok.Builder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

@Builder(toBuilder = true, builderClassName = "Builder")
public record UserGroup(
        Long id,
        String name,
        String description,
        Set<Authority> authorities,
        LocalDateTime creationDate,
        LocalDateTime lastUpdated
)
{
    @Override
    public String toString()
    {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("name", name)
                .append("description", description)
                .append("authorities", authorities)
                .append("creationDate", creationDate)
                .append("lastUpdated", lastUpdated)
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

        UserGroup other = (UserGroup) o;
        return Objects.equals(name, other.name);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(name);
    }
}
