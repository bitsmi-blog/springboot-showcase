package com.bitsmi.springbootshowcase.sampleapps.infrastructure.common.mapper;

import com.bitsmi.springbootshowcase.sampleapps.infrastructure.common.entity.AuthorityEntity;
import com.bitsmi.springbootshowcase.sampleapps.domain.common.model.Authority;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", implementationName = "AuthorityModelMapperImpl")
public interface AuthorityModelMapper
{
    Authority mapDomainFromEntity(AuthorityEntity entity);
}
