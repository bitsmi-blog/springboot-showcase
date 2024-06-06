package com.bitsmi.springbootshowcase.springdatajpa.repositoryextension.infrastructure.inventory.repository;

import com.bitsmi.springbootshowcase.springdatajpa.repositoryextension.infrastructure.common.repository.CustomBaseRepository;
import com.bitsmi.springbootshowcase.springdatajpa.repositoryextension.infrastructure.common.repository.ExternalIdQuerySupportRepository;
import com.bitsmi.springbootshowcase.springdatajpa.repositoryextension.infrastructure.inventory.entity.ProductEntity;

import java.util.Optional;

public interface IProductRepository extends CustomBaseRepository<ProductEntity, Long>, ExternalIdQuerySupportRepository
{
    default Optional<ProductEntity> findByExternalId(String externalId)
    {
        return findByExternalId(ProductEntity.class, externalId);
    }
}
