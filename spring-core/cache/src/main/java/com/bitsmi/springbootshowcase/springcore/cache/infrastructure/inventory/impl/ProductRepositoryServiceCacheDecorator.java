package com.bitsmi.springbootshowcase.springcore.cache.infrastructure.inventory.impl;

import com.bitsmi.springbootshowcase.springcore.cache.domain.common.dto.PaginatedData;
import com.bitsmi.springbootshowcase.springcore.cache.domain.common.dto.Pagination;
import com.bitsmi.springbootshowcase.springcore.cache.domain.inventory.model.Product;
import com.bitsmi.springbootshowcase.springcore.cache.domain.inventory.spi.ProductRepositoryService;
import com.bitsmi.springbootshowcase.springcore.cache.infrastructure.config.MemoizeCacheManagerProvider;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Primary
@ConditionalOnMissingBean(MemoizeCacheManagerProvider.class)
public class ProductRepositoryServiceCacheDecorator implements ProductRepositoryService
{
    private static final String CACHE_ALL_PRODUCTS = "allProducts";
    private static final String CACHE_PRODUCT_BY_EXTERNAL_ID = "productByExternalId";

    private final ProductRepositoryServiceImpl delegate;

    public ProductRepositoryServiceCacheDecorator(ProductRepositoryServiceImpl delegate)
    {
        this.delegate = delegate;
    }

    @Cacheable(cacheNames = CACHE_ALL_PRODUCTS, key = "#pagination.pageNumber")
    @Override
    public PaginatedData<Product> findAllItemSchemas(@NotNull Pagination pagination)
    {
        return delegate.findAllItemSchemas(pagination);
    }

    @Cacheable(cacheNames = CACHE_PRODUCT_BY_EXTERNAL_ID)
    @Override
    public Optional<Product> findProductByExternalId(@NotNull String externalId)
    {
        return delegate.findProductByExternalId(externalId);
    }

    @CacheEvict(cacheNames = CACHE_ALL_PRODUCTS, allEntries = true)
    @Override
    public Product createProduct(@Valid Product product)
    {
        return delegate.createProduct(product);
    }

    @Caching(evict = {
            @CacheEvict(cacheNames = CACHE_ALL_PRODUCTS, allEntries = true),
            @CacheEvict(cacheNames = CACHE_PRODUCT_BY_EXTERNAL_ID, key = "#product.externalId")
    })
    @Override
    public Product updateProduct(@NotNull Long id, @Valid Product product)
    {
        return delegate.updateProduct(id, product);
    }
}
