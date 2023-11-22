package com.bitsmi.springbootshowcase.core.common.mapper;

import com.bitsmi.springbootshowcase.core.common.model.UserGroup;
import com.bitsmi.springbootshowcase.core.common.entity.UserGroupEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        implementationName = "UserGroupMapperImpl",
        uses = {
            IAuthorityMapper.class
        }
)
public interface IUserGroupMapper
{
    UserGroup fromEntity(UserGroupEntity entity);
}
