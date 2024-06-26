package com.bitsmi.springbootshowcase.sampleapps.domain.common;

import com.bitsmi.springbootshowcase.sampleapps.domain.common.model.UserGroup;
import jakarta.validation.constraints.NotNull;

import java.util.Optional;

public interface UserGroupRepositoryService
{
    Optional<UserGroup> findUserGroupByName(@NotNull String name);
}
