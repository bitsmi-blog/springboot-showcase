package com.bitsmi.springbootshowcase.springdatajpa.repositoryextension.infrastructure.inventory.repository;

import com.bitsmi.springbootshowcase.springdatajpa.repositoryextension.infrastructure.inventory.projection.ProductStockProjection;

import java.util.Optional;

public interface ProductStockProjectionRepository
{
    Optional<ProductStockProjection> findStockProjectionByExternalId(String externalId);
}
