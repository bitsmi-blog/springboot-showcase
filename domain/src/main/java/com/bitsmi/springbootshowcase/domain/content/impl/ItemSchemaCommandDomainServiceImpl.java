package com.bitsmi.springbootshowcase.domain.content.impl;

import com.bitsmi.springbootshowcase.domain.content.IItemSchemaCommandDomainService;
import com.bitsmi.springbootshowcase.domain.content.model.ItemSchema;
import com.bitsmi.springbootshowcase.domain.content.spi.IItemSchemaPersistenceService;

public class ItemSchemaCommandDomainServiceImpl implements IItemSchemaCommandDomainService
{
    private final IItemSchemaPersistenceService itemSchemaPersistenceService;

    public ItemSchemaCommandDomainServiceImpl(IItemSchemaPersistenceService itemSchemaPersistenceService)
    {
        this.itemSchemaPersistenceService = itemSchemaPersistenceService;
    }

    @Override
    public ItemSchema createItemSchema(ItemSchema itemSchema)
    {
        return itemSchemaPersistenceService.createItemSchema(itemSchema);
    }
}
