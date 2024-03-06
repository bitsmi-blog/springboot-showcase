package com.bitsmi.springbootshowcase.infrastructure.content.projection;

public interface IItemSchemaSummaryProjection
{
    String getExternalId();
    String getName();
    Long getFieldsCount();
}
