package com.bitsmi.springbootshowcase.infrastructure.common.mapper;

import com.bitsmi.springbootshowcase.infrastructure.common.entity.UserEntity;
import com.bitsmi.springbootshowcase.domain.common.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        implementationName = "UserModelMapperImpl",
        uses = {
                IUserGroupModelMapper.class
        }
)
public interface IUserModelMapper
{
    User fromEntity(UserEntity entity);
}
