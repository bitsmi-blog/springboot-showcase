package com.bitsmi.springbootshowcase.springdatajpa.repositoryextension.infrastructure.inventory.projection;

public interface ProductStockProjection
{
    String getExternalId();
    String getName();
    int getTotalStock();
}
