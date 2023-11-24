package com.bitsmi.springbootshowcase.core.content.mapper;

import com.bitsmi.springbootshowcase.core.content.entity.ItemEntity;
import com.bitsmi.springbootshowcase.core.content.model.Item;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        implementationName = "ItemMapperImpl",
        uses = {
            IItemSchemaMapper.class,
            ITagMapper.class,
            IItemFieldMapper.class,
        }
)
public interface IItemMapper
{
    Item fromEntity(ItemEntity entity);
}
