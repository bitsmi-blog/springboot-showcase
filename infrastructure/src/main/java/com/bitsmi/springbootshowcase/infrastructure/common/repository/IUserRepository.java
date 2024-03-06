package com.bitsmi.springbootshowcase.infrastructure.common.repository;

import com.bitsmi.springbootshowcase.infrastructure.common.projection.IUserSummaryProjection;
import com.bitsmi.springbootshowcase.infrastructure.common.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<UserEntity, Long>
{
    Optional<UserEntity> findByUsername(String username);

    Optional<IUserSummaryProjection> findSummaryProjectionByUsername(String username);
}
