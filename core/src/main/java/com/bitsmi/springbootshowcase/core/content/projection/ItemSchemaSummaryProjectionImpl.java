package com.bitsmi.springbootshowcase.core.content.projection;

public class ItemSchemaSummaryProjectionImpl implements IItemSchemaSummaryProjection
{
    private final String externalId;
    private final String name;
    private final Long fieldsCount;

    public ItemSchemaSummaryProjectionImpl(String externalId, String name, Long fieldsCount)
    {
        this.externalId = externalId;
        this.name = name;
        this.fieldsCount = fieldsCount;
    }

    @Override
    public String getExternalId() {
        return externalId;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Long getFieldsCount() {
        return fieldsCount;
    }
}
