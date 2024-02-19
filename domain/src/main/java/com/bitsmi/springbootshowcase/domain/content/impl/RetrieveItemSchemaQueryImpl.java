package com.bitsmi.springbootshowcase.domain.content.impl;

import com.bitsmi.springbootshowcase.domain.common.dto.Page;
import com.bitsmi.springbootshowcase.domain.common.dto.PagedData;
import com.bitsmi.springbootshowcase.domain.content.IRetrieveItemSchemaQuery;
import com.bitsmi.springbootshowcase.domain.content.model.ItemSchema;
import com.bitsmi.springbootshowcase.domain.content.spi.IItemSchemaPersistenceService;

import java.util.List;

public class RetrieveItemSchemaQueryImpl implements IRetrieveItemSchemaQuery
{
    private final IItemSchemaPersistenceService itemSchemaPersistenceService;

    public RetrieveItemSchemaQueryImpl(IItemSchemaPersistenceService itemSchemaPersistenceService)
    {
        this.itemSchemaPersistenceService = itemSchemaPersistenceService;
    }

    @Override
    public List<ItemSchema> retrieveAllItemSchemas()
    {
        return itemSchemaPersistenceService.findAllItemSchemas();
    }

    @Override
    public PagedData<ItemSchema> retrieveAllItemSchemas(Page page)
    {
        return itemSchemaPersistenceService.findAllItemSchemas(page);
    }
}
