package com.bitsmi.springbootshowcase.sampleapps.infrastructure.common.mapper;

import com.bitsmi.springbootshowcase.sampleapps.domain.common.model.User;
import com.bitsmi.springbootshowcase.sampleapps.infrastructure.common.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",
        implementationName = "UserModelMapperImpl",
        uses = {
                UserGroupModelMapper.class
        }
)
public interface UserModelMapper
{
    User mapDomainFromEntity(UserEntity entity);
    UserEntity mapEntityFromDomain(User domain);
    @Mapping(target = "groups", ignore = true)
    UserEntity mapEntityFromDomainExcludingUserGroups(User domain);
}
