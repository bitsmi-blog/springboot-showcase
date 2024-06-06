package com.bitsmi.springbootshowcase.springdatajpa.repositoryextension.infrastructure.inventory.repository;

import com.bitsmi.springbootshowcase.springdatajpa.repositoryextension.infrastructure.common.repository.CustomBaseRepository;
import com.bitsmi.springbootshowcase.springdatajpa.repositoryextension.infrastructure.inventory.entity.StockEntity;

public interface IStockRepository extends CustomBaseRepository<StockEntity, Long>
{

}
