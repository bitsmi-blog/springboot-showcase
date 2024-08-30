package com.bitsmi.springbootshowcase.clients.fluentclient.inventory.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Objects;

@Builder(toBuilder = true, builderClassName = "Builder")
public record NewCategoryRequest(
        @NotNull @Valid CategoryData data
) {
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("data", data)
                .build();
    }

    @Override
    public boolean equals(Object o) {
        return this == o
                || o instanceof NewCategoryRequest other
                && Objects.equals(data, other.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(data);
    }
}
