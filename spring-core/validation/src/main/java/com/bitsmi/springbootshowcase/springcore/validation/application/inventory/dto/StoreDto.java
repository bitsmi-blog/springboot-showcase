package com.bitsmi.springbootshowcase.springcore.validation.application.inventory.dto;

import com.bitsmi.springbootshowcase.springcore.validation.application.util.AdditionalFieldsValidationGroup;
import com.bitsmi.springbootshowcase.springcore.validation.application.util.MandatoryValidationGroup;
import com.bitsmi.springbootshowcase.springcore.validation.application.util.OptionalValidationGroup;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.Objects;

@Builder(toBuilder = true, builderClassName = "Builder")
public record StoreDto(
        @NotNull(groups = { MandatoryValidationGroup.class }, message = "ExternalId must not be null")
        String externalId,
        @NotNull(groups = { MandatoryValidationGroup.class }, message = "Name must not be null")
        String name,
        @NotNull(groups = { OptionalValidationGroup.class, AdditionalFieldsValidationGroup.class}, message = "Address must not be null")
        String address
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
