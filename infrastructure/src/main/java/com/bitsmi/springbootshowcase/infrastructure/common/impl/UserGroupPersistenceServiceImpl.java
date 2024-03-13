package com.bitsmi.springbootshowcase.infrastructure.common.impl;

import com.bitsmi.springbootshowcase.domain.common.model.UserGroup;
import com.bitsmi.springbootshowcase.domain.common.spi.IUserGroupPersistenceService;
import com.bitsmi.springbootshowcase.infrastructure.InfrastructureConstants;
import com.bitsmi.springbootshowcase.infrastructure.common.mapper.IUserGroupModelMapper;
import com.bitsmi.springbootshowcase.infrastructure.common.repository.IUserGroupRepository;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

@Service
@Validated
public class UserGroupPersistenceServiceImpl implements IUserGroupPersistenceService
{
    @Autowired
    private IUserGroupRepository userGroupRepository;
    @Autowired
    private IUserGroupModelMapper userGroupModelMapper;

    @Cacheable(cacheNames = InfrastructureConstants.CACHE_USER_GROUP_BY_NAME)
    @Override
    public Optional<UserGroup> findUserGroupByName(@NotNull String name)
    {
        return userGroupRepository.findByName(name)
                .map(userGroupModelMapper::fromEntity);
    }
}
