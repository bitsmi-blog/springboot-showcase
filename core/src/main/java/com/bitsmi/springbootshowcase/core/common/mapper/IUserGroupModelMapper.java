package com.bitsmi.springbootshowcase.core.common.mapper;

import com.bitsmi.springbootshowcase.core.common.entity.UserGroupEntity;
import com.bitsmi.springbootshowcase.domain.common.model.UserGroup;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        implementationName = "UserGroupModelMapperImpl",
        uses = {
            IAuthorityModelMapper.class
        }
)
public interface IUserGroupModelMapper
{
    UserGroup fromEntity(UserGroupEntity entity);
}
