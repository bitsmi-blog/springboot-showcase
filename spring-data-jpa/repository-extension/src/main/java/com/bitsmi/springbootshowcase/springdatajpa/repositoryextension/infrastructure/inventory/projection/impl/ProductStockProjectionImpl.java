package com.bitsmi.springbootshowcase.springdatajpa.repositoryextension.infrastructure.inventory.projection.impl;

import com.bitsmi.springbootshowcase.springdatajpa.repositoryextension.infrastructure.inventory.projection.ProductStockProjection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Objects;

@Getter
@AllArgsConstructor
public class ProductStockProjectionImpl implements ProductStockProjection
{
    private final String externalId;
    private final String name;
    private final int totalStock;

    @Override
    public int hashCode() {
        return Objects.hash(externalId);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("externalId", externalId)
                .append("name", name)
                .append("totalStock", totalStock)
                .build();
    }

    @Override
    public boolean equals(Object o) {
        return this == o
                || o instanceof ProductStockProjectionImpl other
                && Objects.equals(externalId, other.externalId);
    }
}
