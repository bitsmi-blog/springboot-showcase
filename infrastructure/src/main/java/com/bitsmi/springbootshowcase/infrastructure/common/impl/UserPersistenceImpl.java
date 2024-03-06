package com.bitsmi.springbootshowcase.infrastructure.common.impl;

import com.bitsmi.springbootshowcase.infrastructure.common.entity.UserEntity;
import com.bitsmi.springbootshowcase.infrastructure.common.mapper.IUserModelMapper;
import com.bitsmi.springbootshowcase.infrastructure.common.mapper.IUserSummaryModelMapper;
import com.bitsmi.springbootshowcase.infrastructure.common.repository.IUserRepository;
import com.bitsmi.springbootshowcase.domain.common.model.User;
import com.bitsmi.springbootshowcase.domain.common.model.UserSummary;
import com.bitsmi.springbootshowcase.domain.common.spi.IUserPersistenceService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Service
@Validated
public class UserPersistenceImpl implements IUserPersistenceService
{
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private IUserModelMapper userModelMapper;
    @Autowired
    private IUserSummaryModelMapper userSummaryModelMapper;

    @Override
    public Long countAllUsers()
    {
        return userRepository.count();
    }

    @Override
    public Optional<User> findUserByUsername(@NotNull String username)
    {
        return userRepository.findByUsername(username)
                .map(userModelMapper::fromEntity);
    }

    @Override
    public Optional<UserSummary> findUserSummaryByUsername(@NotNull String username)
    {
        return userRepository.findSummaryProjectionByUsername(username)
                .map(userSummaryModelMapper::fromProjection);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public User createUser(@NotNull String username, @NotNull String encodedPassword, @NotNull List<String> groups)
    {
        UserEntity entity = UserEntity.builder()
                .username(username)
                .password(encodedPassword)
                .active(Boolean.TRUE)
                .build();

        entity = userRepository.save(entity);

        return userModelMapper.fromEntity(entity);
    }
}
