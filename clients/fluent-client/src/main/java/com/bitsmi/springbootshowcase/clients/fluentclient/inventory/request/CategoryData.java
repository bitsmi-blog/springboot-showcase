package com.bitsmi.springbootshowcase.clients.fluentclient.inventory.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Objects;

@Builder(toBuilder = true, builderClassName = "Builder")
public record CategoryData(
        @NotNull
        String externalId,
        @NotNull
        String name
)
{
    @Override
    public String toString()
    {
        return new ToStringBuilder(this)
                .append("externalId", externalId)
                .append("name", name)
                .build();
    }

    @Override
    public boolean equals(Object o)
    {
        return this == o
                || o instanceof CategoryData other
                && Objects.equals(externalId, other.externalId);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(externalId);
    }
}
