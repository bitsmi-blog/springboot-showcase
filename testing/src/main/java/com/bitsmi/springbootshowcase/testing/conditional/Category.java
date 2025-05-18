package com.bitsmi.springbootshowcase.testing;

import java.util.Objects;
import lombok.Builder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Builder(toBuilder = true, builderClassName = "Builder")
public record Category(
    String externalId,
    String name
) {
    @Override
    public String toString()
    {
        return new ToStringBuilder(this)
            .append("externalId", externalId)
            .append("name", name)
            .build();
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(externalId);
    }

    @Override
    public boolean equals(Object o)
    {
        return this == o
            || o instanceof Category other
            && Objects.equals(externalId, other.externalId);
    }
}
