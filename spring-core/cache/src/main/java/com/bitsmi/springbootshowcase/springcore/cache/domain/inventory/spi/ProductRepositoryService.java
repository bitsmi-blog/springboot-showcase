package com.bitsmi.springbootshowcase.springcore.cache.domain.inventory.spi;

import com.bitsmi.springbootshowcase.springcore.cache.domain.common.dto.PaginatedData;
import com.bitsmi.springbootshowcase.springcore.cache.domain.common.dto.Pagination;
import com.bitsmi.springbootshowcase.springcore.cache.domain.inventory.model.Product;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.Optional;

public interface ProductRepositoryService
{
    PaginatedData<Product> findAllItemSchemas(@NotNull Pagination pagination);

    Optional<Product> findProductByExternalId(@NotNull String externalId);

    Product createProduct(@Valid Product product);
    Product updateProduct(@NotNull Long id, @Valid Product product);
}
