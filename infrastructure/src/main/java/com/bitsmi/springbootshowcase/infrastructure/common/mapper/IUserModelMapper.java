package com.bitsmi.springbootshowcase.infrastructure.common.mapper;

import com.bitsmi.springbootshowcase.domain.common.model.User;
import com.bitsmi.springbootshowcase.infrastructure.common.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",
        implementationName = "UserModelMapperImpl",
        uses = {
                IUserGroupModelMapper.class
        }
)
public interface IUserModelMapper
{
    User fromEntity(UserEntity entity);
    UserEntity fromDomain(User domain);
    @Mapping(target = "groups", ignore = true)
    UserEntity fromDomainExcludingUserGroups(User domain);
}
