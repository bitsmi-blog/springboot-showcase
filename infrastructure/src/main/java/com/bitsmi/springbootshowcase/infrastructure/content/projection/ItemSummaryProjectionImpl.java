package com.bitsmi.springbootshowcase.infrastructure.content.projection;

import com.bitsmi.springbootshowcase.infrastructure.content.entity.ItemStatus;

public class ItemSummaryProjectionImpl implements IItemSummaryProjection
{
    private final String name;
    private final ItemStatus status;
    private final String schemaExternalId;
    private final Long fieldsCount;

    public ItemSummaryProjectionImpl(
            String name,
            ItemStatus status,
            String schemaExternalId,
            Long fieldsCount
    ) {
        this.name = name;
        this.status = status;
        this.schemaExternalId = schemaExternalId;
        this.fieldsCount = fieldsCount;
    }

    @Override
    public String getName()
    {
        return name;
    }

    @Override
    public ItemStatus getStatus()
    {
        return status;
    }

    @Override
    public String getSchemaExternalId()
    {
        return schemaExternalId;
    }

    @Override
    public Long getFieldsCount()
    {
        return fieldsCount;
    }
}
