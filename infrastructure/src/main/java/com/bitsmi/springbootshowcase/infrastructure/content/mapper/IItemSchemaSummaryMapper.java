package com.bitsmi.springbootshowcase.infrastructure.content.mapper;

import com.bitsmi.springbootshowcase.infrastructure.content.projection.IItemSchemaSummaryProjection;
import com.bitsmi.springbootshowcase.domain.content.model.ItemSchemaSummary;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        implementationName = "ItemSchemaSummaryMapperImpl"
)
public interface IItemSchemaSummaryMapper
{
    ItemSchemaSummary fromProjection(IItemSchemaSummaryProjection projection);
}
