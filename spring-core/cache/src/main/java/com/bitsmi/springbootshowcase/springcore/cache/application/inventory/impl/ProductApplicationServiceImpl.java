package com.bitsmi.springbootshowcase.springcore.cache.application.inventory.impl;

import com.bitsmi.springbootshowcase.springcore.cache.application.inventory.ProductApplicationService;
import com.bitsmi.springbootshowcase.springcore.cache.domain.inventory.model.Product;
import com.bitsmi.springbootshowcase.springcore.cache.domain.inventory.ProductDomainRepository;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

@Component
@Validated
public class ProductApplicationServiceImpl implements ProductApplicationService
{
    private final ProductDomainRepository productRepositoryService;

    public ProductApplicationServiceImpl(ProductDomainRepository productRepositoryService)
    {
        this.productRepositoryService = productRepositoryService;
    }

    @Override
    public Optional<Product> findProductByExternalId(@NotNull String externalId)
    {
        // Make 2 calls so we can see a cache hit
        productRepositoryService.findProductByExternalId(externalId);
        return productRepositoryService.findProductByExternalId(externalId);
    }
}
