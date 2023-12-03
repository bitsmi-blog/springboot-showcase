package com.bitsmi.springbootshowcase.core.content.mapper;

import com.bitsmi.springbootshowcase.core.content.projection.IItemSchemaSummaryProjection;
import com.bitsmi.springbootshowcase.core.content.model.ItemSchemaSummary;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        implementationName = "ItemSchemaSummaryModelMapperImpl"
)
public interface IItemSchemaSummaryModelMapper
{
    ItemSchemaSummary fromProjection(IItemSchemaSummaryProjection projection);
}
