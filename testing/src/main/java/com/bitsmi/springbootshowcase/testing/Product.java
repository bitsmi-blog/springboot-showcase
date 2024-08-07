package com.bitsmi.springbootshowcase.testing;

import java.time.LocalDateTime;
import java.util.Objects;
import lombok.Builder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Builder(toBuilder = true, builderClassName = "Builder")
public record Product(
    Long id,
    String externalId,
    String name,
    Category category,
    LocalDateTime creationDate,
    LocalDateTime lastUpdated
)
{
    @Override
    public String toString()
    {
        return new ToStringBuilder(this)
            .append("id", id)
            .append("externalId", externalId)
            .append("name", name)
            .append("category", category)
            .append("creationDate", creationDate)
            .append("lastUpdated", lastUpdated)
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
            || o instanceof Product other
            && Objects.equals(externalId, other.externalId);
    }
}
