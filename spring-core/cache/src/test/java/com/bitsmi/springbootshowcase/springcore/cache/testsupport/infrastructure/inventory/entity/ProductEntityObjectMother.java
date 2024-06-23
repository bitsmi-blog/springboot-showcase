package com.bitsmi.springbootshowcase.springcore.cache.testsupport.infrastructure.inventory.entity;

import com.bitsmi.springbootshowcase.springcore.cache.infrastructure.inventory.entity.ProductEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class ProductEntityObjectMother
{
    public static final Long ID_PRODUCT1 = 1001L;
    public static final Long ID_PRODUCT2 = 1002L;
    public static final String EXTERNAL_ID_PRODUCT1 = "product-1";
    public static final String EXTERNAL_ID_PRODUCT2 = "product-2";

    public static ProductEntity product1()
    {
        return builder()
                .product1()
                .build();
    }

    public static ProductEntity product2()
    {
        return builder()
                .product2()
                .build();
    }

    public static Builder builder()
    {
        return new Builder();
    }

    public static final class Builder
    {
        private final ProductEntity.Builder delegate = ProductEntity.builder();

        public Builder product1()
        {
            LocalDateTime timestamp = LocalDateTime.of(LocalDate.of(2024, 1, 1), LocalTime.MIN);
            delegate.id(ID_PRODUCT1)
                    .version(1L)
                    .externalId(EXTERNAL_ID_PRODUCT1)
                    .name("Test product 1")
                    .creationDate(timestamp)
                    .lastUpdated(timestamp);

            return this;
        }

        public Builder product2()
        {
            LocalDateTime timestamp = LocalDateTime.of(LocalDate.of(2024, 1, 1), LocalTime.MIN);
            delegate.id(ID_PRODUCT2)
                    .version(1L)
                    .externalId(EXTERNAL_ID_PRODUCT2)
                    .name("Test product 2")
                    .creationDate(timestamp)
                    .lastUpdated(timestamp);

            return this;
        }

        public Builder id(Long id)
        {
            delegate.id(id);
            return this;
        }

        public Builder externalId(String externalId)
        {
            delegate.externalId(externalId);
            return this;
        }

        public Builder name(String name)
        {
            delegate.name(name);
            return this;
        }

        public ProductEntity build()
        {
            return delegate.build();
        }
    }
}
