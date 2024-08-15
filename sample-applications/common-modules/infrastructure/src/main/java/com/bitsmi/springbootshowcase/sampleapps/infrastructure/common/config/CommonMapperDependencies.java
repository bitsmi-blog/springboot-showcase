package com.bitsmi.springbootshowcase.sampleapps.infrastructure.common.config;

import com.bitsmi.springbootshowcase.sampleapps.infrastructure.common.mapper.PageRequestMapper;
import com.bitsmi.springbootshowcase.sampleapps.infrastructure.common.mapper.PaginatedDataMapper;
import com.bitsmi.springbootshowcase.sampleapps.infrastructure.common.mapper.UserGroupModelMapper;
import com.bitsmi.springbootshowcase.sampleapps.infrastructure.common.mapper.UserModelMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
@Getter
public class CommonMapperDependencies
{
    private final PageRequestMapper pageRequestMapper;
    private final PaginatedDataMapper paginatedDataMapper;

    private final UserModelMapper userModelMapper;
    private final UserGroupModelMapper userGroupModelMapper;
}
