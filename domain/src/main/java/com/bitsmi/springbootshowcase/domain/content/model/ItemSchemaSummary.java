package com.bitsmi.springbootshowcase.domain.content.model;

import lombok.Builder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Objects;

@Builder(toBuilder = true, builderClassName = "Builder")
public record ItemSchemaSummary(
        String externalId,
        String name,
        Long fieldsCount
)
{
    @Override
    public String toString()
    {
        return new ToStringBuilder(this)
                .append("externalId", externalId)
                .append("name", name)
                .append("fieldsCount", fieldsCount)
                .build();
    }

    @Override
    public boolean equals(Object o)
    {
        return this == o
                || o instanceof ItemSchemaSummary other
                    && Objects.equals(externalId, other.externalId);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(externalId);
    }
}
