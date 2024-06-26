package com.bitsmi.springbootshowcase.sampleapps.infrastructure.common.config;

import com.bitsmi.springbootshowcase.sampleapps.infrastructure.common.repository.UserGroupRepository;
import com.bitsmi.springbootshowcase.sampleapps.infrastructure.common.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
@Getter
public class CommonRepositoryDependencies
{
    private final UserGroupRepository userGroupRepository;
    private final UserRepository userRepository;
}
