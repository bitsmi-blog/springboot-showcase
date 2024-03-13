package com.bitsmi.springbootshowcase.domain.content.impl;

import com.bitsmi.springbootshowcase.domain.common.dto.Pagination;
import com.bitsmi.springbootshowcase.domain.common.dto.PagedData;
import com.bitsmi.springbootshowcase.domain.content.IItemSchemaDomainQueryService;
import com.bitsmi.springbootshowcase.domain.content.model.ItemSchema;
import com.bitsmi.springbootshowcase.domain.content.spi.IItemSchemaPersistenceService;
import java.util.List;

public class ItemSchemaDomainQueryServiceImpl implements IItemSchemaDomainQueryService
{
    private final IItemSchemaPersistenceService itemSchemaPersistenceService;

    public ItemSchemaDomainQueryServiceImpl(IItemSchemaPersistenceService itemSchemaPersistenceService)
    {
        this.itemSchemaPersistenceService = itemSchemaPersistenceService;
    }

    @Override
    public List<ItemSchema> findAllItemSchemas()
    {
        return itemSchemaPersistenceService.findAllItemSchemas();
    }

    @Override
    public PagedData<ItemSchema> findAllItemSchemas(Pagination page)
    {
        return itemSchemaPersistenceService.findAllItemSchemas(page);
    }
}
