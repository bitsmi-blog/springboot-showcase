package com.bitsmi.springbootshowcase.clients.fluentclient.inventory.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Objects;

@Builder(toBuilder = true, builderClassName = "Builder")
public record ProductData(
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
    public int hashCode()
    {
        return Objects.hash(name);
    }

    @Override
    public boolean equals(Object o)
    {
        return this == o
                || o instanceof ProductData other
                && Objects.equals(externalId, other.externalId);
    }
}
