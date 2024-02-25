package com.bitsmi.springbootshowcase.core.common.mapper;

import com.bitsmi.springbootshowcase.core.common.entity.AuthorityEntity;
import com.bitsmi.springbootshowcase.domain.common.model.Authority;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", implementationName = "AuthorityModelMapperImpl")
public interface IAuthorityModelMapper
{
    Authority fromEntity(AuthorityEntity entity);
}
