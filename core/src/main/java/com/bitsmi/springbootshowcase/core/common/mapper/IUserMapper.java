package com.bitsmi.springbootshowcase.core.common.mapper;

import com.bitsmi.springbootshowcase.core.common.domain.User;
import com.bitsmi.springbootshowcase.core.common.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        implementationName = "UserMapperImpl",
        uses = {
                IUserGroupMapper.class
        }
)
public interface IUserMapper
{
    User fromEntity(UserEntity entity);
}
