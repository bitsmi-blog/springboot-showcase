package com.bitsmi.springbootshowcase.sampleapps.infrastructure.common.impl;

import com.bitsmi.springbootshowcase.sampleapps.domain.common.UserDomainRepository;
import com.bitsmi.springbootshowcase.sampleapps.domain.common.dto.PaginatedData;
import com.bitsmi.springbootshowcase.sampleapps.domain.common.dto.Pagination;
import com.bitsmi.springbootshowcase.sampleapps.domain.common.exception.ElementNotFoundException;
import com.bitsmi.springbootshowcase.sampleapps.domain.common.model.User;
import com.bitsmi.springbootshowcase.sampleapps.domain.common.model.UserGroup;
import com.bitsmi.springbootshowcase.sampleapps.infrastructure.common.entity.UserEntity;
import com.bitsmi.springbootshowcase.sampleapps.infrastructure.common.entity.UserGroupEntity;
import com.bitsmi.springbootshowcase.sampleapps.infrastructure.common.mapper.PageRequestMapper;
import com.bitsmi.springbootshowcase.sampleapps.infrastructure.common.mapper.PaginatedDataMapper;
import com.bitsmi.springbootshowcase.sampleapps.infrastructure.common.mapper.UserModelMapper;
import com.bitsmi.springbootshowcase.sampleapps.infrastructure.common.repository.UserGroupRepository;
import com.bitsmi.springbootshowcase.sampleapps.infrastructure.common.repository.UserRepository;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Validated
public class UserDomainRepositoryImpl implements UserDomainRepository
{
    private final UserRepository userRepository;
    private final UserGroupRepository userGroupRepository;
    private final UserModelMapper userModelMapper;
    private final PageRequestMapper pageRequestMapper;
    private final PaginatedDataMapper paginatedDataMapper;

    public UserDomainRepositoryImpl(
            UserRepository userRepository,
            UserGroupRepository userGroupRepository,
            UserModelMapper userModelMapper,
            PageRequestMapper pageRequestMapper,
            PaginatedDataMapper paginatedDataMapper
    ) {
        this.userRepository = userRepository;
        this.userGroupRepository = userGroupRepository;
        this.userModelMapper = userModelMapper;
        this.pageRequestMapper = pageRequestMapper;
        this.paginatedDataMapper = paginatedDataMapper;
    }

    @Override
    public PaginatedData<User> findAllUsers(Pagination page)
    {
        final Pageable pageable = pageRequestMapper.fromPagination(page);

        final org.springframework.data.domain.Page<UserEntity> entityPage = userRepository.findAll(pageable);

        return paginatedDataMapper.fromPage(entityPage, userModelMapper::mapDomainFromEntity);
    }

    @Override
    public Long countAllUsers()
    {
        return userRepository.count();
    }

    @Override
    public Optional<User> findUserById(@NotNull Long id)
    {
        return userRepository.findById(id)
                .map(userModelMapper::mapDomainFromEntity);
    }

    @Override
    public Optional<User> findUserByUsername(@NotNull String username)
    {
        return userRepository.findByUsername(username)
                .map(userModelMapper::mapDomainFromEntity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public User createUser(@NotNull User user)
    {
        UserEntity entity = userModelMapper.mapEntityFromDomainExcludingUserGroups(user);
        entity.setGroups(collectUserGroupEntities(user));
        entity = userRepository.save(entity);

        return userModelMapper.mapDomainFromEntity(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public User updateUser(@NotNull Long id, @NotNull User user)
    {
        UserEntity entity = userRepository.findById(id)
                .orElseThrow(() -> new ElementNotFoundException(
                        UserEntity.class.getName(),
                        "User with id (%s) not found".formatted(id)
                ));

        entity.setUsername(user.username());
        entity.setPassword(user.password());
        entity.setCompleteName(user.completeName());
        entity.setActive(user.active());
        entity.setGroups(collectUserGroupEntities(user));
        entity = userRepository.save(entity);

        return userModelMapper.mapDomainFromEntity(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteUser(@NotNull Long id)
    {
        userRepository.findById(id)
                .ifPresent(userRepository::delete);
    }

    private Set<UserGroupEntity> collectUserGroupEntities(User user)
    {
        return user.groups()
                .stream()
                .map(UserGroup::name)
                .map(userGroupRepository::findByName)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());
    }
}
