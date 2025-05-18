package com.bitsmi.springbootshowcase.testing.db.repository;

import com.bitsmi.springbootshowcase.testing.db.entity.StockEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<StockEntity, Long>
{

}
