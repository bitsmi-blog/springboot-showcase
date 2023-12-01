package com.bitsmi.springbootshowcase.core.common.mapper;

import com.bitsmi.springbootshowcase.core.common.model.Authority;
import com.bitsmi.springbootshowcase.core.common.entity.AuthorityEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", implementationName = "AuthorityModelMapperImpl")
public interface IAuthorityModelMapper
{
    Authority fromEntity(AuthorityEntity entity);
}
