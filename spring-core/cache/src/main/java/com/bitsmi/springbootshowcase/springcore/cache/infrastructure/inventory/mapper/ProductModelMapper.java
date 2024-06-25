package com.bitsmi.springbootshowcase.springcore.cache.infrastructure.inventory.mapper;

import com.bitsmi.springbootshowcase.springcore.cache.domain.inventory.model.Product;
import com.bitsmi.springbootshowcase.springcore.cache.infrastructure.inventory.entity.ProductEntity;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface ProductModelMapper
{
    Product mapDomainFromEntity(ProductEntity entity);
}
