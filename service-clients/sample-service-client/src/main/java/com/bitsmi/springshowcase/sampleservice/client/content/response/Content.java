package com.bitsmi.springshowcase.sampleservice.client.content.response;

import lombok.Builder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.time.LocalDateTime;
import java.util.Objects;

@Builder(toBuilder = true, builderClassName = "Builder")
public record Content(
        Long id,
        String externalId,
        String name,
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
                .append("creationDate", creationDate)
                .append("lastUpdated", lastUpdated)
                .build();
    }

    @Override
    public boolean equals(Object o)
    {
        return this == o
                || o instanceof Content other
                && Objects.equals(externalId, other.externalId);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(externalId);
    }
}
