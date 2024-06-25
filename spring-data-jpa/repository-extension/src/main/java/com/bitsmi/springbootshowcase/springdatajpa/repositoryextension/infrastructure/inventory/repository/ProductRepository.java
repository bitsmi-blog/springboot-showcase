package com.bitsmi.springbootshowcase.springdatajpa.repositoryextension.infrastructure.inventory.repository;

import com.bitsmi.springbootshowcase.springdatajpa.repositoryextension.infrastructure.common.repository.CustomBaseRepository;
import com.bitsmi.springbootshowcase.springdatajpa.repositoryextension.infrastructure.common.repository.ExternalIdQuerySupportRepository;
import com.bitsmi.springbootshowcase.springdatajpa.repositoryextension.infrastructure.inventory.entity.ProductEntity;
import com.bitsmi.springbootshowcase.springdatajpa.repositoryextension.infrastructure.inventory.projection.ProductSummaryProjection;

import java.util.Optional;

public interface ProductRepository extends
        CustomBaseRepository<ProductEntity, Long>,
        ExternalIdQuerySupportRepository,
        ProductStockProjectionRepository
{
    default Optional<ProductEntity> findByExternalId(String externalId)
    {
        return findByExternalId(ProductEntity.class, externalId);
    }

    Optional <ProductSummaryProjection> findSummaryProjectionByExternalId(String externalId);
}
