package com.bitsmi.springbootshowcase.springdatajpa.repositoryextension.infrastructure.inventory.repository;

import com.bitsmi.springbootshowcase.springdatajpa.repositoryextension.infrastructure.common.repository.CustomBaseRepository;
import com.bitsmi.springbootshowcase.springdatajpa.repositoryextension.infrastructure.common.repository.ExternalIdQuerySupportRepository;
import com.bitsmi.springbootshowcase.springdatajpa.repositoryextension.infrastructure.inventory.entity.StoreEntity;

import java.util.Optional;

public interface IStoreRepository extends CustomBaseRepository<StoreEntity, Long>, ExternalIdQuerySupportRepository
{
    default Optional<StoreEntity> findByExternalId(String externalId)
    {
        return findByExternalId(StoreEntity.class, externalId);
    }
}
