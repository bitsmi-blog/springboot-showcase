package com.bitsmi.springbootshowcase.springcore.cache.infrastructure.inventory.impl;

import com.bitsmi.springbootshowcase.springcore.cache.domain.common.dto.PaginatedData;
import com.bitsmi.springbootshowcase.springcore.cache.domain.common.dto.Pagination;
import com.bitsmi.springbootshowcase.springcore.cache.domain.common.exception.ElementAlreadyExistsException;
import com.bitsmi.springbootshowcase.springcore.cache.domain.common.exception.ElementNotFoundException;
import com.bitsmi.springbootshowcase.springcore.cache.domain.inventory.model.Product;
import com.bitsmi.springbootshowcase.springcore.cache.domain.inventory.spi.ProductRepositoryService;
import com.bitsmi.springbootshowcase.springcore.cache.infrastructure.common.mapper.PageRequestMapper;
import com.bitsmi.springbootshowcase.springcore.cache.infrastructure.common.mapper.PaginatedDataMapper;
import com.bitsmi.springbootshowcase.springcore.cache.infrastructure.inventory.entity.ProductEntity;
import com.bitsmi.springbootshowcase.springcore.cache.infrastructure.inventory.mapper.ProductModelMapper;
import com.bitsmi.springbootshowcase.springcore.cache.infrastructure.inventory.repository.ProductRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ProductRepositoryServiceImpl implements ProductRepositoryService
{
    private static final String CACHE_ALL_PRODUCTS = "allProducts";
    private static final String CACHE_PRODUCT_BY_EXTERNAL_ID = "productByExternalId";

    private final ProductRepository productRepository;
    private final ProductModelMapper productModelMapper;
    private final PageRequestMapper pageRequestMapper;
    private final PaginatedDataMapper springPaginatedDataMapper;

    public ProductRepositoryServiceImpl(
            ProductRepository productRepository,
            ProductModelMapper productModelMapper,
            PageRequestMapper pageRequestMapper,
            PaginatedDataMapper paginatedDataMapper
    ) {
        this.productRepository = productRepository;
        this.productModelMapper = productModelMapper;
        this.pageRequestMapper = pageRequestMapper;
        this.springPaginatedDataMapper = paginatedDataMapper;
    }

    @Override
    public PaginatedData<Product> findAllItemSchemas(@NotNull Pagination pagination)
    {
        final Pageable pageable = pageRequestMapper.fromPagination(pagination);

        final Page<ProductEntity> entityPage = productRepository.findAll(pageable);

        return springPaginatedDataMapper.fromPage(entityPage, productModelMapper::mapDomainFromEntity);
    }

    @Override
    public Optional<Product> findProductByExternalId(@NotNull String externalId)
    {
        return productRepository.findByExternalId(externalId)
                .map(productModelMapper::mapDomainFromEntity);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Product createProduct(@Valid Product product)
    {
        if(productRepository.existsByExternalId(product.externalId())) {
            throw new ElementAlreadyExistsException(Product.class.getSimpleName(), "externalId:" + product.externalId());
        }

        final ProductEntity entity = ProductEntity.builder()
                .externalId(product.externalId())
                .name(product.name())
                .build();

        return productModelMapper.mapDomainFromEntity(productRepository.save(entity));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Product updateProduct(@NotNull Long id, @Valid Product product)
    {
        ProductEntity entity = productRepository.findById(id)
                .orElseThrow(() -> new ElementNotFoundException(Product.class.getSimpleName(), "externalId:" + product.externalId()));

        entity.setExternalId(product.externalId());
        entity.setName(product.name());

        return productModelMapper.mapDomainFromEntity(productRepository.save(entity));
    }
}
