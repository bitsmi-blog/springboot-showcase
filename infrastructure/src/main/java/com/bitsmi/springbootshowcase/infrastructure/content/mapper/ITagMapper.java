package com.bitsmi.springbootshowcase.infrastructure.content.mapper;

import com.bitsmi.springbootshowcase.infrastructure.content.entity.TagEntity;
import com.bitsmi.springbootshowcase.domain.content.model.Tag;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        implementationName = "TagMapperImpl",
        uses = {
                IItemSchemaMapper.class
        }
)
public interface ITagMapper
{
    Tag fromEntity(TagEntity entity);
}
