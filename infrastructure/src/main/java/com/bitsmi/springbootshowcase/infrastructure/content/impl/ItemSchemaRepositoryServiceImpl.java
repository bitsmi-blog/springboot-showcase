package com.bitsmi.springbootshowcase.infrastructure.content.impl;

import com.bitsmi.springbootshowcase.domain.common.dto.PaginatedData;
import com.bitsmi.springbootshowcase.domain.common.dto.Pagination;
import com.bitsmi.springbootshowcase.domain.common.exception.ElementAlreadyExistsException;
import com.bitsmi.springbootshowcase.domain.common.exception.ElementNotFoundException;
import com.bitsmi.springbootshowcase.domain.common.util.ValidToUpdate;
import com.bitsmi.springbootshowcase.domain.content.model.ItemSchema;
import com.bitsmi.springbootshowcase.domain.content.spi.IItemSchemaRepositoryService;
import com.bitsmi.springbootshowcase.infrastructure.InfrastructureConstants;
import com.bitsmi.springbootshowcase.infrastructure.content.mapper.ContentServiceClientPaginatedResponseMapper;
import com.bitsmi.springbootshowcase.infrastructure.content.mapper.IItemSchemaMapper;
import com.bitsmi.springshowcase.contentservice.client.ContentServiceClient;
import com.bitsmi.springshowcase.contentservice.client.common.exception.ClientErrorServiceException;
import com.bitsmi.springshowcase.contentservice.client.common.response.PaginatedResponse;
import com.bitsmi.springshowcase.contentservice.client.schema.request.ItemSchemaData;
import com.bitsmi.springshowcase.contentservice.client.schema.request.ItemSchemaFieldData;
import com.bitsmi.springshowcase.contentservice.client.schema.request.SchemaSetSelector;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Validated
public class ItemSchemaRepositoryServiceImpl implements IItemSchemaRepositoryService
{
    private final ContentServiceClient contentServiceClient;
    private final IItemSchemaMapper itemSchemaMapper;
    private final ContentServiceClientPaginatedResponseMapper paginatedDataMapper;

    public ItemSchemaRepositoryServiceImpl(
            ContentServiceClient contentServiceClient,
            IItemSchemaMapper itemSchemaMapper,
            ContentServiceClientPaginatedResponseMapper paginatedDataMapper
    ) {
        this.contentServiceClient = contentServiceClient;
        this.itemSchemaMapper = itemSchemaMapper;
        this.paginatedDataMapper = paginatedDataMapper;
    }

    @Override
    public List<ItemSchema> findAllItemSchemas()
    {
        List<ItemSchema> results = new ArrayList<>();

        PaginatedResponse<com.bitsmi.springshowcase.contentservice.client.schema.response.ItemSchema> response = contentServiceClient.schemas()
                .list()
                .get();
        results.addAll(collectAndMapItemSchemasFromPaginatedResponse(response));

        Optional<com.bitsmi.springshowcase.contentservice.client.common.response.Pagination> optNextPage = response.nextPage();
        while(optNextPage.isPresent()) {
            response = contentServiceClient.schemas()
                    .list()
                    .paginate(optNextPage.get().pageNumber(), optNextPage.get().pageSize())
                    .get();
            results.addAll(collectAndMapItemSchemasFromPaginatedResponse(response));
        }

        return results;
    }

    @Cacheable(cacheNames = InfrastructureConstants.CACHE_ALL_SCHEMAS, key = "#pagination.pageNumber")
    @Override
    public PaginatedData<ItemSchema> findAllItemSchemas(@NotNull Pagination pagination)
    {
        PaginatedResponse<com.bitsmi.springshowcase.contentservice.client.schema.response.ItemSchema> response = contentServiceClient.schemas()
                .list()
                .paginate(pagination.pageNumber(), pagination.pageSize())
                .get();

        return paginatedDataMapper.fromPaginatedResponse(response, itemSchemaMapper::fromClientResponse);
    }

    @Cacheable(cacheNames = InfrastructureConstants.CACHE_SCHEMA_BY_EXTERNAL_ID)
    @Override
    public Optional<ItemSchema> findItemSchemaByExternalId(@NotNull String externalId)
    {
        return contentServiceClient.schemas(SchemaSetSelector.externalId(externalId))
                .list()
                .get()
                .content()
                .stream()
                .findFirst()
                .map(itemSchemaMapper::fromClientResponse);
    }

    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(cacheNames = InfrastructureConstants.CACHE_ALL_SCHEMAS, allEntries = true)
    @Override
    public ItemSchema createItemSchema(@Valid ItemSchema itemSchema)
    {
        final Set<ItemSchemaFieldData> schemaFieldsData = itemSchema.fields().stream()
                .map(field -> ItemSchemaFieldData.builder()
                        .name(field.name())
                        .dataType(com.bitsmi.springshowcase.contentservice.client.schema.shared.DataType.valueOf(field.dataType().name()))
                        .comments(field.comments())
                        .build()
                )
                .collect(Collectors.toSet());
        final ItemSchemaData data = ItemSchemaData.builder()
                .externalId(itemSchema.externalId())
                .name(itemSchema.name())
                .fields(schemaFieldsData)
                .build();

        try {
            final com.bitsmi.springshowcase.contentservice.client.schema.response.ItemSchema response = contentServiceClient.schema(data)
                    .create();

            return itemSchemaMapper.fromClientResponse(response);
        }
        catch(ClientErrorServiceException e) {
            if ("409".equals(e.getErrorCode())) {
                throw new ElementAlreadyExistsException(ItemSchema.class.getSimpleName(), "externalId:" + itemSchema.externalId());
            }
            throw e;
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Caching(evict = {
            @CacheEvict(cacheNames = InfrastructureConstants.CACHE_ALL_SCHEMAS, allEntries = true),
            @CacheEvict(cacheNames = InfrastructureConstants.CACHE_SCHEMA_BY_EXTERNAL_ID, key = "#itemSchema.externalId")
    })
    @Override
    public ItemSchema updateItemSchema(@Valid @ValidToUpdate ItemSchema itemSchema)
    {
        final Set<ItemSchemaFieldData> schemaFieldsData = itemSchema.fields().stream()
                .map(field -> ItemSchemaFieldData.builder()
                        .name(field.name())
                        .dataType(com.bitsmi.springshowcase.contentservice.client.schema.shared.DataType.valueOf(field.dataType().name()))
                        .comments(field.comments())
                        .build()
                )
                .collect(Collectors.toSet());
        final ItemSchemaData data = ItemSchemaData.builder()
                .externalId(itemSchema.externalId())
                .name(itemSchema.name())
                .fields(schemaFieldsData)
                .build();

        try {
            final com.bitsmi.springshowcase.contentservice.client.schema.response.ItemSchema response = contentServiceClient.schema(itemSchema.id())
                    .update(data);

            return itemSchemaMapper.fromClientResponse(response);
        }
        catch(ClientErrorServiceException e) {
            if ("404".equals(e.getErrorCode())) {
                throw new ElementNotFoundException(ItemSchema.class.getSimpleName(), "externalId:" + itemSchema.externalId());
            }
            throw e;
        }
    }

    private List<ItemSchema> collectAndMapItemSchemasFromPaginatedResponse(PaginatedResponse<com.bitsmi.springshowcase.contentservice.client.schema.response.ItemSchema> response)
    {
        return response.content()
                .stream()
                .map(itemSchemaMapper::fromClientResponse)
                .toList();
    }
}
