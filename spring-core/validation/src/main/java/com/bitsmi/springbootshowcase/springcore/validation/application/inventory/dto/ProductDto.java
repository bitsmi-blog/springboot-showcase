package com.bitsmi.springbootshowcase.springcore.validation.application.inventory.dto;

import com.bitsmi.springbootshowcase.springcore.validation.domain.util.UniqueValues;
import com.bitsmi.springbootshowcase.springcore.validation.domain.util.ValidExternalId;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Builder(toBuilder = true, builderClassName = "Builder")
public record ProductDto(
        @ValidExternalId(message = "ExternalId must not be blank")
        String externalId,
        @NotNull(message = "Name must not be null")
        String name,
        @UniqueValues
//        @UniqueValues(field = "externalId")
        List<@Valid CategoryDto> categories,
        @PositiveOrZero(message = "Quantity must not be positive or zero")
        @Max(value = 1_000, message = "Quantity must not be greater that {value}")
        Integer quantity,
        @PastOrPresent(message = "AvailableSince must be a present or past date")
        LocalDateTime availableSince
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
                || o instanceof ProductDto other
                && Objects.equals(externalId, other.externalId);
    }
}
