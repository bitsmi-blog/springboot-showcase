package com.bitsmi.springbootshowcase.sampleapps.infrastructure.common.mapper;

import com.bitsmi.springbootshowcase.sampleapps.infrastructure.common.entity.UserGroupEntity;
import com.bitsmi.springbootshowcase.sampleapps.domain.common.model.UserGroup;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        implementationName = "UserGroupModelMapperImpl",
        uses = {
            AuthorityModelMapper.class
        }
)
public interface UserGroupModelMapper
{
    UserGroup mapDomainFromEntity(UserGroupEntity entity);
}
