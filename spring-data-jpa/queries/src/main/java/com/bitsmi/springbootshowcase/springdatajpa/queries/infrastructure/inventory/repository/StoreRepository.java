package com.bitsmi.springbootshowcase.springdatajpa.queries.infrastructure.inventory.repository;

import com.bitsmi.springbootshowcase.springdatajpa.queries.infrastructure.inventory.entity.StoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<StoreEntity, Long> {

}
