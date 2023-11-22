package com.bitsmi.springbootshowcase.core.common.impl;

import com.bitsmi.springbootshowcase.core.common.IUserManagementService;
import com.bitsmi.springbootshowcase.core.common.model.User;
import com.bitsmi.springbootshowcase.core.common.entity.UserEntity;
import com.bitsmi.springbootshowcase.core.common.mapper.IUserMapper;
import com.bitsmi.springbootshowcase.core.common.repository.IUserGroupRepository;
import com.bitsmi.springbootshowcase.core.common.repository.IUserRepository;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserManagementServiceImpl implements IUserManagementService
{
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private IUserGroupRepository userGroupRepository;
    @Autowired
    private IUserMapper userMapper;

    @Override
    public boolean existUsers()
    {
        return userRepository.count()>0;
    }

    @Override
    public Optional<User> findUserByUsername(@NotNull String username)
    {
        return userRepository.findByUsername(username)
                .map(userMapper::fromEntity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public User createUser(@NotNull String username, @NotNull String encodedPassword, @NotNull List<String>groups)
    {
        UserEntity entity = UserEntity.builder()
                .username(username)
                .password(encodedPassword)
                .active(Boolean.TRUE)
                .build();

        entity = userRepository.save(entity);

        return userMapper.fromEntity(entity);
    }
}
