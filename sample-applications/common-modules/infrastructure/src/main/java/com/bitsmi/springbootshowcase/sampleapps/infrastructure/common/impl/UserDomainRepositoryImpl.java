package com.bitsmi.springbootshowcase.sampleapps.infrastructure.common.impl;

import com.bitsmi.springbootshowcase.sampleapps.domain.common.model.User;
import com.bitsmi.springbootshowcase.sampleapps.domain.common.model.UserGroup;
import com.bitsmi.springbootshowcase.sampleapps.domain.common.model.UserSummary;
import com.bitsmi.springbootshowcase.sampleapps.domain.common.UserDomainRepository;
import com.bitsmi.springbootshowcase.sampleapps.infrastructure.common.entity.UserEntity;
import com.bitsmi.springbootshowcase.sampleapps.infrastructure.common.entity.UserGroupEntity;
import com.bitsmi.springbootshowcase.sampleapps.infrastructure.common.mapper.UserModelMapper;
import com.bitsmi.springbootshowcase.sampleapps.infrastructure.common.mapper.UserSummaryModelMapper;
import com.bitsmi.springbootshowcase.sampleapps.infrastructure.common.repository.UserGroupRepository;
import com.bitsmi.springbootshowcase.sampleapps.infrastructure.common.repository.UserRepository;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Validated
public class UserDomainRepositoryImpl implements UserDomainRepository
{
    private final UserRepository userRepository;
    private final UserGroupRepository userGroupRepository;
    private final UserModelMapper userModelMapper;
    private final UserSummaryModelMapper userSummaryModelMapper;

    public UserDomainRepositoryImpl(
            UserRepository userRepository,
            UserGroupRepository userGroupRepository,
            UserModelMapper userModelMapper,
            UserSummaryModelMapper userSummaryModelMapper
    ) {
        this.userRepository = userRepository;
        this.userGroupRepository = userGroupRepository;
        this.userModelMapper = userModelMapper;
        this.userSummaryModelMapper = userSummaryModelMapper;
    }

    @Override
    public Long countAllUsers()
    {
        return userRepository.count();
    }

    @Override
    public Optional<User> findUserByUsername(@NotNull String username)
    {
        return userRepository.findByUsername(username)
                .map(userModelMapper::mapDomainFromEntity);
    }

    @Override
    public Optional<UserSummary> findUserSummaryByUsername(@NotNull String username)
    {
        return userRepository.findSummaryProjectionByUsername(username)
                .map(userSummaryModelMapper::mapDomainFromSummaryProjection);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public User createUser(@NotNull User user)
    {
        UserEntity entity = userModelMapper.mapEntityFromDomainExcludingUserGroups(user);
        Set<UserGroupEntity> groupEntities = user.groups()
                .stream()
                .map(UserGroup::name)
                .map(userGroupRepository::findByName)
                .map(Optional::get)
                .collect(Collectors.toSet());
        entity.setGroups(groupEntities);
        entity = userRepository.save(entity);

        return userModelMapper.mapDomainFromEntity(entity);
    }
}
