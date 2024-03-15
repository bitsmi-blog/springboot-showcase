package com.bitsmi.springbootshowcase.api.content.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;
import java.util.Objects;

@UniqueItemSchemaFields
@Builder(toBuilder = true, builderClassName = "Builder")
public record CreateItemSchemaRequest(
        @NotNull
        String externalId,
        @NotNull
        String name,
        List<@Valid ItemSchemaField> fields
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
                || o instanceof CreateItemSchemaRequest other
                    && Objects.equals(externalId, other.externalId);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(externalId);
    }
}
