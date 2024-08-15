package com.bitsmi.springbootshowcase.sampleapps.webmvc.web.user.mapper;

import com.bitsmi.springbootshowcase.sampleapps.domain.common.model.UserGroup;
import com.bitsmi.springbootshowcase.sampleapps.webmvc.web.user.controller.response.User;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface UserApiMapper
{
    User mapResponseFromModel(com.bitsmi.springbootshowcase.sampleapps.domain.common.model.User model);

    default String mapResponseGroupNameFromModel(UserGroup model)
    {
        return model.name();
    }
}
