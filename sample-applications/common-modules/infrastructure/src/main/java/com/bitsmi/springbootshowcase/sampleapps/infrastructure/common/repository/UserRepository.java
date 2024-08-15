package com.bitsmi.springbootshowcase.sampleapps.infrastructure.common.repository;

import com.bitsmi.springbootshowcase.sampleapps.infrastructure.common.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long>
{
    Optional<UserEntity> findByUsername(String username);
}
