package com.bitsmi.springbootshowcase.domain.content.impl;

import com.bitsmi.springbootshowcase.domain.content.IItemSchemaDomainCommandService;
import com.bitsmi.springbootshowcase.domain.content.model.ItemSchema;
import com.bitsmi.springbootshowcase.domain.content.spi.IItemSchemaPersistenceService;

public class ItemSchemaDomainCommandServiceImpl implements IItemSchemaDomainCommandService
{
    private final IItemSchemaPersistenceService itemSchemaPersistenceService;

    public ItemSchemaDomainCommandServiceImpl(IItemSchemaPersistenceService itemSchemaPersistenceService)
    {
        this.itemSchemaPersistenceService = itemSchemaPersistenceService;
    }

    @Override
    public ItemSchema createItemSchema(ItemSchema itemSchema)
    {
        return itemSchemaPersistenceService.createItemSchema(itemSchema);
    }
}
