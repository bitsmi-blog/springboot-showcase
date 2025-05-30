package com.bitsmi.springbootshowcase.springdatajpa.queries.infrastructure.inventory.repository;

import com.bitsmi.springbootshowcase.springdatajpa.queries.infrastructure.inventory.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProductRepository extends JpaRepository<ProductEntity, Long>, JpaSpecificationExecutor<ProductEntity> {

}
