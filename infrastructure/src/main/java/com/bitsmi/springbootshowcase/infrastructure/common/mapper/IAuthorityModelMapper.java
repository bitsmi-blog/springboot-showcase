package com.bitsmi.springbootshowcase.infrastructure.common.mapper;

import com.bitsmi.springbootshowcase.infrastructure.common.entity.AuthorityEntity;
import com.bitsmi.springbootshowcase.domain.common.model.Authority;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", implementationName = "AuthorityModelMapperImpl")
public interface IAuthorityModelMapper
{
    Authority fromEntity(AuthorityEntity entity);
}
