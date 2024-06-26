package com.bitsmi.springbootshowcase.sampleapps.infrastructure.common.config;

import com.bitsmi.springbootshowcase.sampleapps.infrastructure.common.mapper.UserGroupModelMapper;
import com.bitsmi.springbootshowcase.sampleapps.infrastructure.common.mapper.UserModelMapper;
import com.bitsmi.springbootshowcase.sampleapps.infrastructure.common.mapper.UserSummaryModelMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
@Getter
public class CommonMapperDependencies
{
    private final UserModelMapper userModelMapper;
    private final UserSummaryModelMapper userSummaryModelMapper;
    private final UserGroupModelMapper userGroupModelMapper;
}
