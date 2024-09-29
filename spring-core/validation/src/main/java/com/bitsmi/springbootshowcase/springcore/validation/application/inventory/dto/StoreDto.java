package com.bitsmi.springbootshowcase.springcore.validation.application.inventory.dto;

import com.bitsmi.springbootshowcase.springcore.validation.application.util.ValidationGroups;
import com.bitsmi.springbootshowcase.springcore.validation.application.util.ValidationGroups.ValidateAdditionalFields;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.Objects;

@Builder(toBuilder = true, builderClassName = "Builder")
public record StoreDto(
        @NotNull(message = "ExternalId must not be null")
        String externalId,
        @NotNull(message = "Name must not be null")
        String name,
        @NotNull(groups = { ValidationGroups.ValidateOptional.class }, message = "Address must not be null")
        String address,
        @NotNull(groups = { ValidationGroups.ValidateOptional.class, ValidateAdditionalFields.class }, message = "ExtraInfo must not be null")
        String extraInfo
) {
    @Override
    public String toString() {
        return externalId;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(externalId);
    }

    @Override
    public boolean equals(Object o) {
        return this == o
                || o instanceof StoreDto other
                && Objects.equals(externalId, other.externalId);
    }
}
