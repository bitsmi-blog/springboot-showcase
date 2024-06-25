package com.bitsmi.springbootshowcase.springcore.cache.infrastructure.inventory.repository;

import com.bitsmi.springbootshowcase.springcore.cache.infrastructure.inventory.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<ProductEntity, Long>
{
    Optional<ProductEntity> findByExternalId(String externalId);
    boolean existsByExternalId(String externalId);
}
