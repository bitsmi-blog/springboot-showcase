package com.bitsmi.springbootshowcase.sampleapps.infrastructure.common.impl;

import com.bitsmi.springbootshowcase.sampleapps.domain.common.model.UserGroup;
import com.bitsmi.springbootshowcase.sampleapps.domain.common.UserGroupRepositoryService;
import com.bitsmi.springbootshowcase.sampleapps.infrastructure.InfrastructureConstants;
import com.bitsmi.springbootshowcase.sampleapps.infrastructure.common.mapper.UserGroupModelMapper;
import com.bitsmi.springbootshowcase.sampleapps.infrastructure.common.repository.UserGroupRepository;
import jakarta.validation.constraints.NotNull;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

@Service
@Validated
public class UserGroupRepositoryServiceImpl implements UserGroupRepositoryService
{
    private final UserGroupRepository userGroupRepository;
    private final UserGroupModelMapper userGroupModelMapper;

    public UserGroupRepositoryServiceImpl(
            UserGroupRepository userGroupRepository,
            UserGroupModelMapper userGroupModelMapper
    ) {
        this.userGroupRepository = userGroupRepository;
        this.userGroupModelMapper = userGroupModelMapper;
    }

    @Cacheable(cacheNames = InfrastructureConstants.CACHE_USER_GROUP_BY_NAME)
    @Override
    public Optional<UserGroup> findUserGroupByName(@NotNull String name)
    {
        return userGroupRepository.findByName(name)
                .map(userGroupModelMapper::mapDomainFromEntity);
    }
}
