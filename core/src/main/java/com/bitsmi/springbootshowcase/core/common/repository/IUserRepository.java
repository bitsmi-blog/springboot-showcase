package com.bitsmi.springbootshowcase.core.common.repository;

import com.bitsmi.springbootshowcase.core.common.entity.IUserSummaryProjection;
import com.bitsmi.springbootshowcase.core.common.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<UserEntity, Long>
{
    Optional<UserEntity> findByUsername(String username);

    Optional<IUserSummaryProjection> findProjectedByUsername(String username);
}
