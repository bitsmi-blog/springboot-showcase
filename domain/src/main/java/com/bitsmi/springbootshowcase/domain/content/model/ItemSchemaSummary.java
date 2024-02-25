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
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ItemSchemaSummary other = (ItemSchemaSummary) o;
        return Objects.equals(externalId, other.externalId);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(externalId);
    }
}
