package com.bitsmi.springbootshowcase.domain.content;

import com.bitsmi.springbootshowcase.domain.content.model.ItemSchema;

public interface IItemSchemaCommandDomainService
{
    ItemSchema createItemSchema(ItemSchema itemSchema);
}
