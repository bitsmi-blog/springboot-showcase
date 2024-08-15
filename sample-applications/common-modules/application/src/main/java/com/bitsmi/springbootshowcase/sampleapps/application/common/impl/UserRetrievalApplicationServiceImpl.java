package com.bitsmi.springbootshowcase.sampleapps.application.common.impl;

import com.bitsmi.springbootshowcase.sampleapps.application.common.UserRetrievalApplicationService;
import com.bitsmi.springbootshowcase.sampleapps.domain.common.UserDomainRepository;
import com.bitsmi.springbootshowcase.sampleapps.domain.common.dto.PaginatedData;
import com.bitsmi.springbootshowcase.sampleapps.domain.common.dto.Pagination;
import com.bitsmi.springbootshowcase.sampleapps.domain.common.model.User;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

@Service
@Validated
public class UserRetrievalApplicationServiceImpl implements UserRetrievalApplicationService {
    private final UserDomainRepository userDomainRepository;

    public UserRetrievalApplicationServiceImpl(UserDomainRepository userDomainRepository) {
        this.userDomainRepository = userDomainRepository;
    }

    @Override
    public PaginatedData<User> findAllUsers(Pagination page)
    {
        return userDomainRepository.findAllUsers(page);
    }

    @Override
    public Optional<User> findUserById(@NotNull Long id) {
        return userDomainRepository.findUserById(id);
    }

    @Override
    public Optional<User> findUserByUsername(@NotNull String username) {
        return userDomainRepository.findUserByUsername(username);
    }
}
