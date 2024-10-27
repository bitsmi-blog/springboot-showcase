package com.bitsmi.springbootshowcase.springdatajpa.queries.infrastructure.inventory.repository;

import com.bitsmi.springbootshowcase.springdatajpa.queries.infrastructure.inventory.entity.StockEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<StockEntity, Long>
{

}
