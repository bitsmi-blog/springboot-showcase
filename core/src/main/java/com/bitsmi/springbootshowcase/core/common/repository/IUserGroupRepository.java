package com.bitsmi.springbootshowcase.core.common.repository;

import com.bitsmi.springbootshowcase.core.common.entity.UserGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUserGroupRepository extends JpaRepository<UserGroupEntity, Long>
{
    Optional<UserGroupEntity> findByName(String name);
}
