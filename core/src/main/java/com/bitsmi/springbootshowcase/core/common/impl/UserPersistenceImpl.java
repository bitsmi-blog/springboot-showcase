package com.bitsmi.springbootshowcase.core.common.impl;

import com.bitsmi.springbootshowcase.core.common.mapper.IUserModelMapper;
import com.bitsmi.springbootshowcase.core.common.mapper.IUserSummaryModelMapper;
import com.bitsmi.springbootshowcase.core.common.repository.IUserRepository;
import com.bitsmi.springbootshowcase.domain.common.model.User;
import com.bitsmi.springbootshowcase.domain.common.model.UserSummary;
import com.bitsmi.springbootshowcase.domain.common.spi.IUserPersistenceService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserPersistenceImpl implements IUserPersistenceService
{
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private IUserModelMapper userModelMapper;
    @Autowired
    private IUserSummaryModelMapper userSummaryModelMapper;

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
}
