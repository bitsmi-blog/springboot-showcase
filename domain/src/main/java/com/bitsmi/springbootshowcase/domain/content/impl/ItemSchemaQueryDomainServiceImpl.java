package com.bitsmi.springbootshowcase.domain.content.impl;

import com.bitsmi.springbootshowcase.domain.common.dto.Page;
import com.bitsmi.springbootshowcase.domain.common.dto.PagedData;
import com.bitsmi.springbootshowcase.domain.content.IItemSchemaQueryDomainService;
import com.bitsmi.springbootshowcase.domain.content.model.ItemSchema;
import com.bitsmi.springbootshowcase.domain.content.spi.IItemSchemaPersistenceService;
import java.util.List;

public class ItemSchemaQueryDomainServiceImpl implements IItemSchemaQueryDomainService
{
    private final IItemSchemaPersistenceService itemSchemaPersistenceService;

    public ItemSchemaQueryDomainServiceImpl(IItemSchemaPersistenceService itemSchemaPersistenceService)
    {
        this.itemSchemaPersistenceService = itemSchemaPersistenceService;
    }

    @Override
    public List<ItemSchema> findAllItemSchemas()
    {
        return itemSchemaPersistenceService.findAllItemSchemas();
    }

    @Override
    public PagedData<ItemSchema> findAllItemSchemas(Page page)
    {
        return itemSchemaPersistenceService.findAllItemSchemas(page);
    }
}
