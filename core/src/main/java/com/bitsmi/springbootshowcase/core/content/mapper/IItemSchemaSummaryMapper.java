package com.bitsmi.springbootshowcase.core.content.mapper;

import com.bitsmi.springbootshowcase.core.content.projection.IItemSchemaSummaryProjection;
import com.bitsmi.springbootshowcase.domain.content.model.ItemSchemaSummary;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        implementationName = "ItemSchemaSummaryMapperImpl"
)
public interface IItemSchemaSummaryMapper
{
    ItemSchemaSummary fromProjection(IItemSchemaSummaryProjection projection);
}
