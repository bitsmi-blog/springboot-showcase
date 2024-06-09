package com.bitsmi.springbootshowcase.sampleapps.infrastructure.common.mapper;

import com.bitsmi.springbootshowcase.sampleapps.infrastructure.common.entity.UserGroupEntity;
import com.bitsmi.springbootshowcase.sampleapps.domain.common.model.UserGroup;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        implementationName = "UserGroupModelMapperImpl",
        uses = {
            IAuthorityModelMapper.class
        }
)
public interface IUserGroupModelMapper
{
    UserGroup mapDomainFromEntity(UserGroupEntity entity);
}
