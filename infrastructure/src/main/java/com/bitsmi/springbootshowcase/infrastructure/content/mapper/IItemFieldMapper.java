package com.bitsmi.springbootshowcase.infrastructure.content.mapper;

import com.bitsmi.springbootshowcase.infrastructure.content.entity.ItemFieldEntity;
import com.bitsmi.springbootshowcase.domain.content.model.ItemField;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        implementationName = "ItemFieldMapperImpl",
        uses = {
            IItemSchemaFieldMapper.class
        }
)
public interface IItemFieldMapper
{
    ItemField fromEntity(ItemFieldEntity entity);
}
