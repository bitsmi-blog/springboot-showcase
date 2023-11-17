package com.bitsmi.springbootshowcase.core.common.mapper;

import com.bitsmi.springbootshowcase.core.common.domain.Authority;
import com.bitsmi.springbootshowcase.core.common.entity.AuthorityEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", implementationName = "AuthorityMapperImpl")
public interface IAuthorityMapper
{
    Authority fromEntity(AuthorityEntity entity);
}
