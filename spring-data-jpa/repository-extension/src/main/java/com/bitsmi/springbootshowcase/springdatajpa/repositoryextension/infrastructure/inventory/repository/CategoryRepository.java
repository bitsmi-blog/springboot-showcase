package com.bitsmi.springbootshowcase.springdatajpa.repositoryextension.infrastructure.inventory.repository;

import com.bitsmi.springbootshowcase.springdatajpa.repositoryextension.infrastructure.common.repository.CustomBaseRepository;
import com.bitsmi.springbootshowcase.springdatajpa.repositoryextension.infrastructure.common.repository.ExternalIdQuerySupportRepository;
import com.bitsmi.springbootshowcase.springdatajpa.repositoryextension.infrastructure.inventory.entity.CategoryEntity;

import java.util.Optional;

public interface CategoryRepository extends CustomBaseRepository<CategoryEntity, Long>, ExternalIdQuerySupportRepository
{
    default Optional<CategoryEntity> findByExternalId(String externalId)
    {
        return findByExternalId(CategoryEntity.class, externalId);
    }
}
