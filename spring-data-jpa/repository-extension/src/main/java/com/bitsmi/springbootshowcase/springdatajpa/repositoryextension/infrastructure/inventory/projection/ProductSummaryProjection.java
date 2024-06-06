package com.bitsmi.springbootshowcase.springdatajpa.repositoryextension.infrastructure.inventory.projection;

import org.springframework.beans.factory.annotation.Value;

public interface ProductSummaryProjection
{
    String getExternalId();
    String getName();
    // Spring Data is capable of resolving nested attributes like `category.name` using @Value annotation and `target` as current record reference.
    @Value("#{ target.category.name }")
    String getCategoryName();
}
