package com.bitsmi.springbootshowcase.springcore.validation.application.inventory.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.Objects;

@Builder(toBuilder = true, builderClassName = "Builder")
public record CategoryDto(
        @NotBlank(message = "ExternalId must not be blank")
        String externalId,
        @NotNull(message = "Name must not be null")
        String name
)
{
    @Override
    public String toString()
    {
        return externalId;
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
                || o instanceof CategoryDto other
                && Objects.equals(externalId, other.externalId);
    }
}
