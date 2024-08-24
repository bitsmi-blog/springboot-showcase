package com.bitsmi.springbootshowcase.springcore.validation.application.inventory.dto;

import com.bitsmi.springbootshowcase.springcore.validation.domain.util.ValidExternalId;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.Objects;

@Builder(toBuilder = true, builderClassName = "Builder")
public record NamespacedElementDto(
    @ValidExternalId(message = "Invalid ExternalId")
    NamespacedId externalId,
    @NotNull(message = "Name must not be null")
    String name
) {
    @Override
    public String toString() {
        return externalId.toString();
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(externalId);
    }

    @Override
    public boolean equals(Object o) {
        return this == o
                || o instanceof NamespacedElementDto other
                && Objects.equals(externalId, other.externalId);
    }
}
