package com.bitsmi.springshowcase.contentservice.client.schema.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Objects;
import java.util.Set;

@Builder(toBuilder = true, builderClassName = "Builder")
public record ItemSchemaData(
        @NotNull
        String externalId,
        @NotNull
        String name,
        Set<@Valid ItemSchemaFieldData> fields
)
{
    @Override
    public String toString()
    {
        return new ToStringBuilder(this)
                .append("externalId", externalId)
                .append("name", name)
                .append("fields", fields)
                .build();
    }

    @Override
    public boolean equals(Object o)
    {
        return this == o
                || o instanceof ItemSchemaData other
                && Objects.equals(externalId, other.externalId);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(externalId);
    }
}
