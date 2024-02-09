package com.bitsmi.springbootshowcase.core.common.mapper;

import com.bitsmi.springbootshowcase.core.common.entity.UserEntity;
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
