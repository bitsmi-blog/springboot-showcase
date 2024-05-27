package com.bitsmi.springbootshowcase.sample.domain.content;

import com.bitsmi.springbootshowcase.sample.domain.content.model.ItemSchema;

public interface IItemSchemaDomainCommandService
{
    ItemSchema createItemSchema(ItemSchema itemSchema);
}
