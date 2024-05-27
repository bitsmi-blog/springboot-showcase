package com.bitsmi.springbootshowcase.sample.domain.common.spi;

import com.bitsmi.springbootshowcase.sample.domain.common.model.UserGroup;
import jakarta.validation.constraints.NotNull;

import java.util.Optional;

public interface IUserGroupRepositoryService
{
    Optional<UserGroup> findUserGroupByName(@NotNull String name);
}
