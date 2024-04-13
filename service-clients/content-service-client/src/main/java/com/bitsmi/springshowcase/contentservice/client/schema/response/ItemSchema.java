package com.bitsmi.springshowcase.contentservice.client.schema.response;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

@Builder(toBuilder = true, builderClassName = "Builder")
public record ItemSchema(
        Long id,
        @NotNull
        String externalId,
        @NotNull
        String name,
        Set<@Valid ItemSchemaField> fields,
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
                .append("fields", fields)
                .append("creationDate", creationDate)
                .append("lastUpdated", lastUpdated)
                .build();
    }

    @Override
    public boolean equals(Object o)
    {
        return this == o
                || o instanceof ItemSchema other
                && Objects.equals(externalId, other.externalId);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(externalId);
    }
}
