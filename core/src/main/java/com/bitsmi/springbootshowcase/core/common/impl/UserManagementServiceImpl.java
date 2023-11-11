package com.bitsmi.springbootshowcase.core.common.impl;

import com.bitsmi.springbootshowcase.core.common.IUserManagementService;
import com.bitsmi.springbootshowcase.core.common.domain.User;
import com.bitsmi.springbootshowcase.core.common.repository.IUserRepository;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserManagementServiceImpl implements IUserManagementService
{
    @Autowired
    private IUserRepository userRepository;

    @Override
    public boolean existUsers()
    {
        return userRepository.count()>0;
    }

    @Transactional(rollbackFor = Exception.class)
    public User createUser(@NotNull String username, @NotNull String encodedPassword)
    {
        User user = new User();
        user.setUsername(username);
        user.setPassword(encodedPassword);
        user.setActive(Boolean.TRUE);

        return userRepository.save(user);
    }
}
