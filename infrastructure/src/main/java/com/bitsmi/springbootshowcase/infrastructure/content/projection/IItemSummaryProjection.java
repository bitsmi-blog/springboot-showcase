package com.bitsmi.springbootshowcase.infrastructure.content.projection;

import com.bitsmi.springbootshowcase.infrastructure.content.entity.ItemStatus;

public interface IItemSummaryProjection
{
    String getName();
    ItemStatus getStatus();
    String getSchemaExternalId();
    Long getFieldsCount();
}
