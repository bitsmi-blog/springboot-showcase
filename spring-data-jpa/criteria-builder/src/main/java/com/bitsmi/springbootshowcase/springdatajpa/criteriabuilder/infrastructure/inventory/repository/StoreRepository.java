package com.bitsmi.springbootshowcase.springdatajpa.criteriabuilder.infrastructure.inventory.repository;

import com.bitsmi.springbootshowcase.springdatajpa.criteriabuilder.infrastructure.inventory.entity.StoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<StoreEntity, Long> {

}
