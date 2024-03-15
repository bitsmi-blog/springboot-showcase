package com.bitsmi.springbootshowcase.domain.common.spi;

import com.bitsmi.springbootshowcase.domain.common.model.UserGroup;
import jakarta.validation.constraints.NotNull;

import java.util.Optional;

public interface IUserGroupPersistenceService
{
    Optional<UserGroup> findUserGroupByName(@NotNull String name);
}